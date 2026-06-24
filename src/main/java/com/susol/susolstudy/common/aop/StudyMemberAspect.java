package com.susol.susolstudy.common.aop;

import com.susol.susolstudy.dao.StudyMemberRepository;
import lombok.RequiredArgsConstructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@RequiredArgsConstructor
public class StudyMemberAspect {
    private final StudyMemberRepository studyMemberRepository;

    @Before("@annotation(requiredStudyMember)")
    public void checkStudyMember(JoinPoint joinPoint, RequiredStudyMember requiredStudyMember) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmailId = auth.getName();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        String targetParam = requiredStudyMember.studyIdParam();
        Integer studyId = null;

        for(int i = 0; i <parameters.length; i++) {
            if(parameters[i].getName().equals(targetParam)) {
                studyId = (Integer) args[i];
                break;
            }
        }

        if(studyId == null) {
            throw new IllegalArgumentException("@RequireStudyMember : '" + targetParam + "' 파라미터를 찾을 수 없습니다.");
        }

        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
        if(!isMember) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
    }
}
