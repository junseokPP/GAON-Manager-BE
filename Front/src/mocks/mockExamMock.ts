// 모의고사 Mock 데이터

export type MockExamType = '3월 모평' | '6월 모평' | '9월 모평' | '수능';

export interface MockExamScore {
  id: number;
  studentId: number;
  studentName: string;
  examType: MockExamType;
  korean: number; // 국어
  english: number; // 영어
  math: number; // 수학
  science1: number; // 탐구1
  science2: number; // 탐구2
  totalScore: number;
  date: string; // YYYY-MM-DD
  createdAt: string; // ISO string
}

const STORAGE_KEY = 'mockExamScores';

export const getMockExamScores = (studentId?: number): MockExamScore[] => {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    const scores: MockExamScore[] = JSON.parse(stored);
    if (studentId) {
      return scores.filter((s) => s.studentId === studentId);
    }
    return scores;
  }
  // 초기 mock 데이터
  return [
    {
      id: 1,
      studentId: 1,
      studentName: '홍길동',
      examType: '3월 모평',
      korean: 85,
      english: 90,
      math: 75,
      science1: 80,
      science2: 85,
      totalScore: 415,
      date: '2024-03-15',
      createdAt: new Date('2024-03-15').toISOString(),
    },
    {
      id: 2,
      studentId: 1,
      studentName: '홍길동',
      examType: '6월 모평',
      korean: 88,
      english: 92,
      math: 78,
      science1: 82,
      science2: 87,
      totalScore: 427,
      date: '2024-06-15',
      createdAt: new Date('2024-06-15').toISOString(),
    },
  ];
};

export const saveMockExamScore = (score: Omit<MockExamScore, 'id' | 'totalScore' | 'createdAt'>): MockExamScore => {
  const scores = getMockExamScores();
  const totalScore = score.korean + score.english + score.math + score.science1 + score.science2;
  
  const newScore: MockExamScore = {
    ...score,
    id: Date.now(),
    totalScore,
    createdAt: new Date().toISOString(),
  };
  
  scores.unshift(newScore);
  localStorage.setItem(STORAGE_KEY, JSON.stringify(scores));
  return newScore;
};

export const getMockExamScoresByMonth = (studentId: number, month: string): MockExamScore[] => {
  const scores = getMockExamScores(studentId);
  return scores.filter((s) => s.date.startsWith(month));
};

