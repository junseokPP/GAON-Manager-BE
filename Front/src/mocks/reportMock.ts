// 월별 보고서 Mock 데이터

import { getWordTestHistory } from './wordMock';
import { getPlannerSubmissionHistory } from './plannerMock';
import { getBehaviorRecordsByMonth } from './behaviorMock';
import { getMockExamScoresByMonth } from './mockExamMock';

export interface AttendanceSummary {
  totalDays: number;
  unexcusedLateCount: number;
  unexcusedAbsentCount: number;
  totalStudyHours: string; // "HH:mm" 형식
}

export interface WordTestSummary {
  averageScore: number;
  testCount: number;
  results: Array<{
    date: string;
    score: number;
  }>;
}

export interface PlannerSummary {
  submissionRate: number; // 0-100
  totalDays: number;
  submittedDays: number;
  submittedDates: string[]; // YYYY-MM-DD 배열
}

export interface MonthlyReport {
  studentId: number;
  studentName: string;
  month: string; // YYYY-MM
  attendance: AttendanceSummary;
  wordTest: WordTestSummary;
  planner: PlannerSummary;
}

// 출결 요약 생성
export const getAttendanceSummary = (studentId: number, month: string): AttendanceSummary => {
  // Mock 데이터 (실제로는 출결 API에서 가져와야 함)
  const daysInMonth = new Date(month + '-01').getDate();
  return {
    totalDays: Math.floor(Math.random() * daysInMonth) + 15,
    unexcusedLateCount: Math.floor(Math.random() * 5),
    unexcusedAbsentCount: Math.floor(Math.random() * 3),
    totalStudyHours: `${Math.floor(Math.random() * 50) + 100}시간 ${Math.floor(Math.random() * 60)}분`,
  };
};

// 영단어 테스트 요약 생성
export const getWordTestSummary = (studentId: number, month: string): WordTestSummary => {
  const history = getWordTestHistory(studentId);
  const monthHistory = history.filter((h) => h.date.startsWith(month));
  
  if (monthHistory.length === 0) {
    return {
      averageScore: 0,
      testCount: 0,
      results: [],
    };
  }
  
  const totalScore = monthHistory.reduce((sum, h) => sum + h.score, 0);
  const averageScore = Math.round(totalScore / monthHistory.length);
  
  return {
    averageScore,
    testCount: monthHistory.length,
    results: monthHistory.map((h) => ({
      date: h.date,
      score: h.score,
    })),
  };
};

// 플래너 요약 생성
export const getPlannerSummary = (studentId: number, month: string): PlannerSummary => {
  const submissions = getPlannerSubmissionHistory(studentId, month);
  const daysInMonth = new Date(month + '-01').getDate();
  const submittedDates = submissions.map((s) => s.date);
  
  return {
    submissionRate: Math.round((submissions.length / daysInMonth) * 100),
    totalDays: daysInMonth,
    submittedDays: submissions.length,
    submittedDates,
  };
};

// 월별 보고서 생성
export const getMonthlyReport = (studentId: number, studentName: string, month: string): MonthlyReport => {
  return {
    studentId,
    studentName,
    month,
    attendance: getAttendanceSummary(studentId, month),
    wordTest: getWordTestSummary(studentId, month),
    planner: getPlannerSummary(studentId, month),
  };
};

