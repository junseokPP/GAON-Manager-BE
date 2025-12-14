import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { BehaviorForm } from '../components/BehaviorForm';
import { BehaviorList } from '../components/BehaviorList';
import { 
  getBehaviorRecords, 
  saveBehaviorRecord,
  BehaviorType 
} from '../mocks/behaviorMock';

const Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
`;

const BehaviorCreatePage = () => {
  // Mock 학생 목록 (실제로는 API에서 가져옴)
  const mockStudents = [
    { id: 1, name: '홍길동' },
    { id: 2, name: '이영희' },
    { id: 3, name: '김철수' },
  ];

  const [studentId, setStudentId] = useState<number>(0);
  const [studentName, setStudentName] = useState<string>('');
  const [type, setType] = useState<BehaviorType | ''>('');
  const [detail, setDetail] = useState('');
  const [records, setRecords] = useState(getBehaviorRecords());
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    // 기록 목록 로드
    setRecords(getBehaviorRecords());
  }, []);

  const handleStudentChange = (id: number, name: string) => {
    setStudentId(id);
    setStudentName(name);
  };

  const handleSubmit = () => {
    if (!studentId || !type || !detail.trim()) {
      return;
    }

    setIsSubmitting(true);

    // 저장
    setTimeout(() => {
      saveBehaviorRecord({
        studentId,
        studentName,
        type: type as BehaviorType,
        detail,
        createdBy: '관리자', // 실제로는 로그인한 사용자 정보
      });
      
      // 폼 리셋
      setType('');
      setDetail('');
      setIsSubmitting(false);
      
      // 목록 새로고침
      setRecords(getBehaviorRecords());
    }, 500);
  };

  return (
    <Container>
      <BehaviorForm
        studentId={studentId}
        studentName={studentName}
        type={type}
        detail={detail}
        onStudentChange={handleStudentChange}
        onTypeChange={setType}
        onDetailChange={setDetail}
        onSubmit={handleSubmit}
        isSubmitting={isSubmitting}
        students={mockStudents}
      />
      <BehaviorList records={records} />
    </Container>
  );
};

export default BehaviorCreatePage;

