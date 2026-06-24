package com.susol.susolstudy.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.susol.susolstudy.model.entity.QStudyNote;
import com.susol.susolstudy.model.entity.StudyNote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StudyNoteRepositoryImpl implements StudyNoteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<StudyNote> searchStudyNotes(String userEmailId, String keyword, Integer studyId, Pageable pageable) {
        QStudyNote studyNote = QStudyNote.studyNote;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(studyNote.user.userEmailId.eq(userEmailId));
        builder.and(studyNote.studyNoteIsDelete.isFalse());

        if(keyword != null && !keyword.isBlank()) {
            builder.and(studyNote.studyNoteTitle.contains(keyword));
        }
        if(studyId != null && studyId != 0) {
            builder.and(studyNote.study.studyId.eq(studyId));
        }

        List<StudyNote> content = queryFactory
                .selectFrom(studyNote)
                .where(builder)
                .orderBy(studyNote.studyNoteId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                        queryFactory
                        .select(studyNote.count())
                        .from(studyNote)
                        .where(builder)
                        .fetchOne()
                    ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
