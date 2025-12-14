// 플래너 Mock 데이터

export interface PlannerSubmission {
  id: number;
  studentId: number;
  date: string; // YYYY-MM-DD
  content: string;
  submittedAt: string; // ISO string
}

const STORAGE_KEY = 'plannerSubmissions';

export const getPlannerSubmissions = (studentId: number): PlannerSubmission[] => {
  const stored = localStorage.getItem(`${STORAGE_KEY}_${studentId}`);
  if (stored) {
    return JSON.parse(stored);
  }
  return [];
};

export const getTodayPlannerStatus = (studentId: number): boolean => {
  const submissions = getPlannerSubmissions(studentId);
  const today = new Date().toISOString().split('T')[0];
  return submissions.some((sub) => sub.date === today);
};

export const savePlannerSubmission = (studentId: number, content: string): PlannerSubmission => {
  const submissions = getPlannerSubmissions(studentId);
  const today = new Date().toISOString().split('T')[0];
  
  const newSubmission: PlannerSubmission = {
    id: Date.now(),
    studentId,
    date: today,
    content,
    submittedAt: new Date().toISOString(),
  };
  
  // 오늘 제출 기록이 있으면 업데이트, 없으면 추가
  const existingIndex = submissions.findIndex((sub) => sub.date === today);
  if (existingIndex >= 0) {
    submissions[existingIndex] = newSubmission;
  } else {
    submissions.unshift(newSubmission);
  }
  
  localStorage.setItem(`${STORAGE_KEY}_${studentId}`, JSON.stringify(submissions));
  return newSubmission;
};

export const getPlannerSubmissionHistory = (studentId: number, month?: string): PlannerSubmission[] => {
  const submissions = getPlannerSubmissions(studentId);
  if (!month) {
    return submissions;
  }
  return submissions.filter((sub) => sub.date.startsWith(month));
};

