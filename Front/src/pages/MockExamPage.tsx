import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { MockExamForm } from '../components/MockExamForm';
import { MockExamTable } from '../components/MockExamTable';
import { 
  getMockExamScores, 
  saveMockExamScore,
  MockExamType 
} from '../mocks/mockExamMock';

const Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
`;

const MockExamPage = () => {
  // Mock 학생 목록
  const mockStudents = [
    { id: 1, name: '홍길동' },
    { id: 2, name: '이영희' },
    { id: 3, name: '김철수' },
  ];

  const [studentId, setStudentId] = useState<number>(0);
  const [studentName, setStudentName] = useState<string>('');
  const [examType, setExamType] = useState<MockExamType | ''>('');
  const [korean, setKorean] = useState<number>(0);
  const [english, setEnglish] = useState<number>(0);
  const [math, setMath] = useState<number>(0);
  const [science1, setScience1] = useState<number>(0);
  const [science2, setScience2] = useState<number>(0);
  const [scores, setScores] = useState(getMockExamScores());
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    // 성적 목록 로드
    setScores(getMockExamScores());
  }, []);

  const handleStudentChange = (id: number, name: string) => {
    setStudentId(id);
    setStudentName(name);
  };

  const handleScoreChange = (field: 'korean' | 'english' | 'math' | 'science1' | 'science2', value: number) => {
    switch (field) {
      case 'korean':
        setKorean(value);
        break;
      case 'english':
        setEnglish(value);
        break;
      case 'math':
        setMath(value);
        break;
      case 'science1':
        setScience1(value);
        break;
      case 'science2':
        setScience2(value);
        break;
    }
  };

  const handleSubmit = () => {
    if (!studentId || !examType) {
      return;
    }

    setIsSubmitting(true);

    // 저장
    setTimeout(() => {
      saveMockExamScore({
        studentId,
        studentName,
        examType: examType as MockExamType,
        korean,
        english,
        math,
        science1,
        science2,
        date: new Date().toISOString().split('T')[0],
      });
      
      // 폼 리셋
      setKorean(0);
      setEnglish(0);
      setMath(0);
      setScience1(0);
      setScience2(0);
      setIsSubmitting(false);
      
      // 목록 새로고침
      setScores(getMockExamScores());
    }, 500);
  };

  return (
    <Container>
      <MockExamForm
        studentId={studentId}
        studentName={studentName}
        examType={examType}
        korean={korean}
        english={english}
        math={math}
        science1={science1}
        science2={science2}
        onStudentChange={handleStudentChange}
        onExamTypeChange={setExamType}
        onScoreChange={handleScoreChange}
        onSubmit={handleSubmit}
        isSubmitting={isSubmitting}
        students={mockStudents}
      />
      <MockExamTable scores={scores} />
    </Container>
  );
};

export default MockExamPage;

