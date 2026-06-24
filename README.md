# SusolStudy - 스터디 관리 플랫폼 백엔드

스터디 그룹 생성, 멤버 관리, 스터디 노트 작성, 게시판 기능을 제공하는 Spring Boot 기반 웹 애플리케이션입니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.x |
| View | JSP / JSTL |
| ORM | Spring Data JPA + QueryDSL 5.1.0 |
| Security | Spring Security 6 |
| DB | MySQL |
| Build | Gradle |
| Etc | Lombok, Swagger (SpringDoc), Spring AOP, Spring Scheduler |

---

## 담당 구현 영역

> **김솔민 담당 : Spring Security / 게시판(Post) / 스터디 노트(StudyNote)**

---

## 1. Spring Security

### 인증 흐름

```
사용자 로그인 요청
   ↓
UserDetailService.loadUserByUsername()  (DB에서 이메일로 사용자 조회)
   ↓
UserDetail (UserDetails 구현체, 권한 매핑)
   ↓
SecurityFilterChain 인증 완료 → SecurityContext 에 저장
   ↓
컨트롤러에서 @AuthenticationPrincipal UserDetails 로 현재 사용자 접근
```

### SecurityConfig 주요 설정

**파일 경로:** `src/main/java/com/susol/susolstudy/common/config/SecurityConfig.java`

```java
// 인증 없이 접근 가능한 경로 허용
.authorizeHttpRequests(request -> request
    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
    .requestMatchers("/login", "/", "/auth/**", "/css/**", "/js/**",
                     "/swagger-ui/**", "/v3/api-docs/**").permitAll()
    .anyRequest().authenticated()
)
```

- `DispatcherType.FORWARD` 허용: JSP forward 시 내부 요청이 인증 필터에 막히는 문제 방지
- Swagger 경로 전체 허용: `/swagger-ui/**`, `/v3/api-docs/**`

**CSRF 처리:**

이 프로젝트는 **세션 기반 인증(Form Login)** 을 사용하므로 Spring Security의 CSRF 보호가 기본적으로 활성화됩니다. JSP 뷰에서 `<sec:csrfInput/>` 또는 `${_csrf}` 토큰을 폼에 포함해 CSRF 공격을 방어합니다.

### Remember-Me (자동 로그인)

```java
.rememberMe(remember -> remember
    .userDetailsService(userDetailService)
    .tokenRepository(persistentTokenRepository())  // DB에 토큰 영속 저장
    .tokenValiditySeconds(60 * 60 * 24 * 7)        // 7일 유지
    .rememberMeParameter("remember-me")
)
```

`PersistentTokenRepository` + `JdbcTokenRepositoryImpl`을 사용해 remember-me 토큰을 DB에 저장합니다. 서버 재시작 후에도 자동 로그인이 유지됩니다.

### 로그아웃

```java
.logout(logout -> logout
    .logoutUrl("/auth/logout")
    .logoutSuccessUrl("/main/home")
    .invalidateHttpSession(true)
    .deleteCookies("JSESSIONID", "remember-me")
)
```

세션 무효화 + remember-me 쿠키까지 함께 삭제해 완전한 로그아웃을 처리합니다.

### 인증 예외 처리 (Swagger 대응)

```java
.exceptionHandling(ex -> ex
    .authenticationEntryPoint((request, response, authException) -> {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/html")) {
            response.sendRedirect("/auth/login");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    })
)
```

- HTML 요청 → 로그인 페이지 리다이렉트
- API/Swagger 요청 → 401 JSON 응답 반환 (Swagger UI 에서 로그인 루프에 빠지는 문제 해결)

### UserDetail / UserDetailService

**파일 경로:**
- `src/main/java/com/susol/susolstudy/common/security/UserDetail.java`
- `src/main/java/com/susol/susolstudy/common/security/UserDetailService.java`

```java
// UserDetail: 권한을 "ROLE_" + Permission enum 형태로 변환
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserPermission()));
}

// UserDetailService: 이메일 ID로 사용자 조회
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserEmailId(username)
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
    return new UserDetail(user);
}
```

---

## 2. 게시판 (Post)

### 주요 기능

| 기능 | 경로 |
|------|------|
| 게시물 목록 조회 | `GET /study/{studyId}/post` |
| 게시물 상세 조회 | `GET /study/{studyId}/post/{postId}` |
| 게시물 작성 | `POST /study/{studyId}/post` |
| 게시물 수정 | `POST /study/{studyId}/post/{postId}/update` |
| 게시물 삭제 | `DELETE /study/{studyId}/post/{postId}` |

### JPA - 연관관계 및 엔티티 설계

**파일 경로:** `src/main/java/com/susol/susolstudy/model/entity/Post.java`

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "study_id")
private Study study;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;

@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
private List<PostFile> postFiles = new ArrayList<>();
```

- 연관관계는 모두 `LAZY` 로딩으로 설정해 N+1 문제를 예방합니다.
- `PostFile`은 `CascadeType.REMOVE` + `orphanRemoval = true`로 게시물 삭제 시 첨부파일도 함께 삭제됩니다.

### 정적 팩토리 메서드 패턴

`Post.create()` 정적 팩토리 메서드로 생성 책임을 엔티티 안에 캡슐화했습니다. 생성자는 `PROTECTED`로 막아 외부에서 직접 `new Post()`를 방지합니다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public static Post create(Study study, User user, PostWriteRequestDTO postDTO) {
    Post post = new Post();
    post.study = study;
    post.user = user;
    post.postType = postDTO.getPostType();
    post.postTitle = postDTO.getPostTitle();
    post.postContent = postDTO.getPostContent();
    return post;
}
```

### DTO 활용 전략

**파일 경로:** `src/main/java/com/susol/susolstudy/model/dto/`

컨트롤러 ↔ 서비스 ↔ DB 레이어 사이에서 엔티티를 직접 노출하지 않고 DTO로 변환해 관심사를 분리합니다.

| DTO | 역할 |
|-----|------|
| `PostWriteRequestDTO` | 게시물 작성 요청 데이터 수신 |
| `PostUpdateRequestDTO` | 게시물 수정 요청 데이터 수신 |
| `PostResponseDTO` | 게시물 목록 응답 (필요한 필드만 노출) |
| `PostDetailResponseDTO` | 게시물 상세 응답 (파일 목록 포함) |
| `PostUpdateResponseDTO` | 수정 페이지 초기 데이터 응답 |

각 DTO에는 `entityOf(Entity)` 정적 메서드를 구현해 Service에서 일관된 방식으로 변환합니다.

```java
// PostDetailResponseDTO.java
public static PostDetailResponseDTO entityOf(Post post) {
    return new PostDetailResponseDTO(
            post.getStudy().getStudyTitle(),
            post.getUser().getUserEmailId(),
            post.getPostType().getKoreanName(),
            post.getPostTitle(),
            post.getPostContent(),
            post.getPostViewCount(),
            post.getPostCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            post.getPostUpdatedAt() != null ?
                    post.getPostUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null,
            post.getPostFiles().stream()
                    .map(f -> new PostFileDTO(f.getPostOriginalFileName(), f.getPostRenamedFileName()))
                    .toList()
    );
}
```

### 조회수 중복 방지 (PostReadLog)

**파일 경로:**
- `src/main/java/com/susol/susolstudy/model/entity/PostReadLog.java`
- `src/main/java/com/susol/susolstudy/dao/PostReadLogRepository.java`

게시물 상세 조회 시 오늘 이미 읽은 기록이 있으면 조회수를 올리지 않고, 본인 게시물도 조회수에서 제외합니다.

```java
// PostService.java - getPostByPostId()
boolean isReadToday = postReadLogRepository.existsReadLog(user, post, LocalDate.now());
boolean isMine = checkMyPost(userEmailId, postId);

if (!isReadToday && !isMine) {
    PostReadLog postReadLog = PostReadLog.create(post, user);
    postReadLogRepository.save(postReadLog);
    post.setPostViewCount(post.getPostViewCount() + 1);
}
```

**JPQL 커스텀 쿼리:**

```java
// PostReadLogRepository.java
@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
        "FROM PostReadLog p " +
        "WHERE p.user = :user AND p.post = :post " +
        "AND CAST(p.postReadLogDate AS DATE) = :date")
boolean existsReadLog(@Param("user") User user,
                      @Param("post") Post post,
                      @Param("date") LocalDate now);
```

`CAST(postReadLogDate AS DATE)`로 `LocalDateTime` 타입의 날짜 부분만 추출해 당일 조회 여부를 판별합니다.

### 파일 업로드

```java
// PostService.java - writePostUploadFile()
String renamedFileName = UUID.randomUUID() + ext;  // 중복 방지 파일명 생성
file.transferTo(new File(path + "/" + renamedFileName));
postFileRepository.save(PostFile.create(post, originalFileName, renamedFileName));
```

- 원본 파일명과 UUID로 변환한 저장 파일명을 DB에 함께 저장합니다.
- 업로드 도중 예외 발생 시 이미 저장된 파일을 롤백(삭제) 처리합니다.

### AOP - @RequiredStudyMember

**파일 경로:**
- `src/main/java/com/susol/susolstudy/common/aop/RequiredStudyMember.java`
- `src/main/java/com/susol/susolstudy/common/aop/StudyMemberAspect.java`

스터디 멤버 권한 체크 로직을 AOP로 분리해 Service 메서드에서 반복 코드를 제거합니다.

```java
// 커스텀 어노테이션 선언
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredStudyMember {
    String studyIdParam() default "studyId";
}

// Aspect: 어노테이션이 붙은 메서드 실행 전 스터디 멤버 여부 검증
@Before("@annotation(requiredStudyMember)")
public void checkStudyMember(JoinPoint joinPoint, RequiredStudyMember requiredStudyMember) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmailId = auth.getName();
    // 메서드 파라미터에서 studyId 값 추출 후 멤버 여부 검증
    boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
    if (!isMember) throw new AccessDeniedException("접근 권한이 없습니다.");
}
```

사용 예시:

```java
// PostService.java
@RequiredStudyMember
@Transactional
public List<PostResponseDTO> getPostList(int studyId, String userEmailId) { ... }
```

---

## 3. 스터디 노트 (StudyNote)

### 주요 기능

| 기능 | 경로 |
|------|------|
| 내 노트 목록 (검색/필터/페이징) | `GET /note` |
| 노트 상세 조회 | `GET /note/{studyNoteId}` |
| 노트 작성 | `POST /note/write` |
| 다른 멤버 노트 조회 | `GET /note/member` |
| 노트 삭제 (소프트 삭제) | `POST /note/{studyNoteId}/delete` |

### 공개 범위 (NotePermission)

노트 작성 시 공개 범위를 설정합니다.

| 값 | 설명 |
|----|------|
| `PRIVATE` | 본인만 조회 가능 |
| `MEMBER` | 같은 스터디 멤버에게 공개 |

### QueryDSL - 동적 검색 쿼리

**파일 경로:** `src/main/java/com/susol/susolstudy/dao/StudyNoteRepositoryImpl.java`

키워드, 스터디 ID 필터를 조건부로 조합하는 동적 쿼리를 QueryDSL로 구현했습니다.

```java
BooleanBuilder builder = new BooleanBuilder();
builder.and(studyNote.user.userEmailId.eq(userEmailId));
builder.and(studyNote.studyNoteIsDelete.isFalse());  // 소프트 삭제된 노트 제외

if (keyword != null && !keyword.isBlank()) {
    builder.and(studyNote.studyNoteTitle.contains(keyword));  // 제목 검색
}
if (studyId != null && studyId != 0) {
    builder.and(studyNote.study.studyId.eq(studyId));         // 스터디 필터
}

List<StudyNote> content = queryFactory
        .selectFrom(studyNote)
        .join(studyNote.user).fetchJoin()   // N+1 방지
        .join(studyNote.study).fetchJoin()  // N+1 방지
        .where(builder)
        .orderBy(studyNote.studyNoteId.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
```

`BooleanBuilder`로 조건을 동적으로 추가하고, `fetchJoin()`으로 연관 엔티티를 한 번에 조회해 N+1 문제를 방지합니다.

**Repository 구조 (Custom 패턴):**

```
StudyNoteRepository (JpaRepository + StudyNoteRepositoryCustom)
    ↑ extends
StudyNoteRepositoryCustom (인터페이스 - 커스텀 쿼리 정의)
    ↑ implements
StudyNoteRepositoryImpl (QueryDSL 구현체)
```

### JPQL - 같은 스터디 멤버 노트 조회

**파일 경로:** `src/main/java/com/susol/susolstudy/dao/StudyNoteRepository.java`

서브쿼리로 현재 사용자가 속한 스터디 ID 목록을 구한 뒤, 해당 스터디에서 다른 멤버가 `MEMBER` 공개로 작성한 노트만 조회합니다.

```java
@Query(
    value = """
        SELECT sn FROM StudyNote sn
        JOIN FETCH sn.user
        JOIN FETCH sn.study
        WHERE sn.studyNoteVisibility = 'MEMBER'
        AND sn.studyNoteIsDelete = false
        AND sn.user.userEmailId != :userEmailId
        AND sn.study.studyId IN (
            SELECT sm.study.studyId FROM StudyMember sm
            WHERE sm.user.userEmailId = :userEmailId
        )
    """,
    countQuery = """
        SELECT COUNT(sn) FROM StudyNote sn
        WHERE sn.studyNoteVisibility = 'MEMBER'
        AND sn.studyNoteIsDelete = false
        AND sn.user.userEmailId != :userEmailId
        AND sn.study.studyId IN (
            SELECT sm.study.studyId FROM StudyMember sm
            WHERE sm.user.userEmailId = :userEmailId
        )
    """
)
Page<StudyNote> findMemberStudyNotes(@Param("userEmailId") String userEmailId, Pageable pageable);
```

`countQuery`를 별도 지정해 페이징 카운트 쿼리에서 불필요한 `JOIN FETCH`가 실행되지 않도록 했습니다.

### 소프트 삭제 (Soft Delete)

실제로 레코드를 삭제하지 않고 `studyNoteIsDelete` 플래그를 `true`로 변경합니다. 덕분에 데이터 복구 및 감사 추적이 가능합니다.

```java
// StudyNote.java
public void setStudyNoteIsDelete() {
    this.studyNoteIsDelete = true;
}

// StudyNoteService.java - @Transactional 내에서 dirty checking으로 자동 UPDATE
public void deleteStudyNote(String userEmailId, int studyNoteId) {
    StudyNote studyNote = studyNoteRepository
            .findByStudyNoteIdAndUser_UserEmailId(studyNoteId, userEmailId)
            .orElseThrow(() -> new EntityNotFoundException("게시물이 없습니다."));
    studyNote.setStudyNoteIsDelete();  // 별도 save() 없이 트랜잭션 종료 시 자동 반영
}
```

### 페이징 처리

```java
// StudyNoteController.java
Pageable pageable = PageRequest.of(page, size, Sort.by("studyNoteId").descending());
Page<StudyNoteResponseDTO> studyNotes = service.getAllStudyNote(user.getUsername(), keyword, studyId, pageable);

model.addAttribute("studyNotes", studyNotes.getContent());
model.addAttribute("totalPages", studyNotes.getTotalPages());
model.addAttribute("currentPage", page);
```

`Pageable`을 Service → Repository 까지 그대로 전달하고, QueryDSL에서 `.offset()` / `.limit()` 으로 처리합니다.

---

## 4. Spring Scheduler - 조회 로그 자동 정리

**파일 경로:** `src/main/java/com/susol/susolstudy/common/scheduler/PostReadLogScheduler.java`

매일 자정에 전날 이전의 `PostReadLog` 데이터를 일괄 삭제합니다. 오늘 날짜 기준 이전 데이터를 삭제함으로써 중복 조회 방지 기능은 당일에만 유효하게 동작합니다.

```java
@Component
public class PostReadLogScheduler {

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정 실행
    public void deleteOldReadLogs() {
        postReadLogRepository.deleteBeforeDate(LocalDate.now());
    }
}
```

**JPQL 벌크 삭제 쿼리:**

```java
// PostReadLogRepository.java
@Modifying
@Query("DELETE FROM PostReadLog p WHERE CAST(p.postReadLogDate as DATE) < :today")
void deleteBeforeDate(@Param("today") LocalDate today);
```

- `@Modifying`: INSERT/UPDATE/DELETE 쿼리 실행 시 필수 어노테이션
- `@Scheduled(cron = "0 0 0 * * *")`: 초 분 시 일 월 요일 형식의 cron 표현식
- `SusolstudyApplication`에 `@EnableScheduling`을 선언해 스케줄러를 활성화합니다.

---

## 프로젝트 구조

```
src/main/java/com/susol/susolstudy/
├── common/
│   ├── aop/            # @RequiredStudyMember 커스텀 어노테이션 + Aspect
│   ├── config/         # SecurityConfig, QueryDslConfig, SwaggerConfig
│   ├── exception/      # 전역 예외 핸들러
│   ├── filter/         # LoggingFilter
│   ├── scheduler/      # PostReadLogScheduler
│   └── security/       # UserDetail, UserDetailService
├── controller/         # 각 도메인 컨트롤러
├── dao/                # Repository (JPA + QueryDSL)
├── model/
│   ├── dto/            # 요청/응답 DTO
│   └── entity/         # JPA 엔티티
└── service/            # 비즈니스 로직
```