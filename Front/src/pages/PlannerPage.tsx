import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { PlannerForm } from '../components/PlannerForm';
import { 
  getTodayPlannerStatus, 
  savePlannerSubmission 
} from '../mocks/plannerMock';

const Container = styled.div`
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
`;

const PlannerPage = () => {
  // Mock 학생 ID (실제로는 로그인한 학생 정보에서 가져옴)
  const studentId = 1;
  const studentName = '홍길동';

  const [content, setContent] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    // 오늘 제출 여부 확인
    const submitted = getTodayPlannerStatus(studentId);
    setIsSubmitted(submitted);
  }, []);

  const handleSubmit = () => {
    if (!content.trim()) {
      return;
    }

    setIsSubmitting(true);

    // 제출 저장
    setTimeout(() => {
      savePlannerSubmission(studentId, content);
      setIsSubmitted(true);
      setIsSubmitting(false);
    }, 500);
  };

  return (
    <Container>
      <PlannerForm
        studentName={studentName}
        content={content}
        onChange={setContent}
        onSubmit={handleSubmit}
        isSubmitted={isSubmitted}
        isSubmitting={isSubmitting}
      />
    </Container>
  );
};

export default PlannerPage;

