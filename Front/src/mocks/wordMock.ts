// 영단어 테스트 Mock 데이터

export interface WordTestQuestion {
  id: number;
  word: string;
  meaning: string;
  options: string[];
  correctAnswer: number;
}

export interface WordTestResult {
  id: number;
  date: string; // YYYY-MM-DD
  score: number; // 0-50
  totalQuestions: number;
  correctAnswers: number;
}

export interface WordTestHistory {
  studentId: number;
  results: WordTestResult[];
}

// Mock 테스트 문제 (5문항)
export const mockWordTestQuestions: WordTestQuestion[] = [
  {
    id: 1,
    word: 'abandon',
    meaning: '포기하다',
    options: ['포기하다', '수용하다', '지지하다', '승인하다'],
    correctAnswer: 0,
  },
  {
    id: 2,
    word: 'ability',
    meaning: '능력',
    options: ['무능력', '능력', '장애', '제한'],
    correctAnswer: 1,
  },
  {
    id: 3,
    word: 'absolute',
    meaning: '절대적인',
    options: ['상대적인', '절대적인', '조건부의', '부분적인'],
    correctAnswer: 1,
  },
  {
    id: 4,
    word: 'accomplish',
    meaning: '성취하다',
    options: ['실패하다', '성취하다', '시도하다', '계획하다'],
    correctAnswer: 1,
  },
  {
    id: 5,
    word: 'accurate',
    meaning: '정확한',
    options: ['부정확한', '정확한', '모호한', '불명확한'],
    correctAnswer: 1,
  },
];

// Mock 테스트 결과 저장소 (localStorage 시뮬레이션)
const STORAGE_KEY = 'wordTestResults';

export const getWordTestHistory = (studentId: number): WordTestResult[] => {
  const stored = localStorage.getItem(`${STORAGE_KEY}_${studentId}`);
  if (stored) {
    return JSON.parse(stored);
  }
  // 초기 mock 데이터
  return [
    {
      id: 1,
      date: new Date().toISOString().split('T')[0],
      score: 40,
      totalQuestions: 5,
      correctAnswers: 4,
    },
  ];
};

export const saveWordTestResult = (studentId: number, result: WordTestResult): void => {
  const history = getWordTestHistory(studentId);
  history.unshift(result);
  localStorage.setItem(`${STORAGE_KEY}_${studentId}`, JSON.stringify(history));
};

// 랜덤 점수 생성 (0-50)
export const generateRandomScore = (correctAnswers: number): number => {
  // 정답 개수에 따라 점수 계산 (각 문제 10점)
  return correctAnswers * 10;
};

