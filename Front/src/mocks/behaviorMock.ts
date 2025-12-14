// 학습 태도 Mock 데이터

export type BehaviorType = '수면' | '태도' | '태블릿';

export interface BehaviorRecord {
  id: number;
  studentId: number;
  studentName: string;
  type: BehaviorType;
  detail: string;
  createdAt: string; // ISO string
  createdBy: string; // 관리자 이름
}

const STORAGE_KEY = 'behaviorRecords';

export const getBehaviorRecords = (studentId?: number): BehaviorRecord[] => {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    const records: BehaviorRecord[] = JSON.parse(stored);
    if (studentId) {
      return records.filter((r) => r.studentId === studentId);
    }
    return records;
  }
  // 초기 mock 데이터
  return [
    {
      id: 1,
      studentId: 1,
      studentName: '홍길동',
      type: '태도',
      detail: '집중력이 좋았습니다.',
      createdAt: new Date().toISOString(),
      createdBy: '관리자',
    },
    {
      id: 2,
      studentId: 1,
      studentName: '홍길동',
      type: '수면',
      detail: '충분히 수면을 취한 것으로 보입니다.',
      createdAt: new Date(Date.now() - 86400000).toISOString(),
      createdBy: '관리자',
    },
  ];
};

export const saveBehaviorRecord = (record: Omit<BehaviorRecord, 'id' | 'createdAt'>): BehaviorRecord => {
  const records = getBehaviorRecords();
  const newRecord: BehaviorRecord = {
    ...record,
    id: Date.now(),
    createdAt: new Date().toISOString(),
  };
  records.unshift(newRecord);
  localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
  return newRecord;
};

export const getBehaviorRecordsByMonth = (studentId: number, month: string): BehaviorRecord[] => {
  const records = getBehaviorRecords(studentId);
  return records.filter((r) => r.createdAt.startsWith(month));
};

