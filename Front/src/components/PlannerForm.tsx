import styled from 'styled-components';
import { Card } from './Card';
import { Button } from './Button';
import { Input } from './Input';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const StudentInfo = styled.div`
  margin-bottom: 24px;
  color: #6B7280;
`;

const FormContainer = styled.div`
  margin-bottom: 24px;
`;

const Label = styled.label`
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 8px;
`;

const Textarea = styled.textarea`
  width: 100%;
  min-height: 200px;
  padding: 12px;
  border: 1px solid #E0E0E0;
  border-radius: 8px;
  font-size: 14px;
  font-family: inherit;
  resize: vertical;
  color: #333333;

  &:focus {
    outline: none;
    border-color: #FFC107;
    box-shadow: 0 0 0 3px rgba(255, 193, 7, 0.1);
  }
`;

const SuccessMessage = styled.div`
  padding: 16px;
  background-color: #f0fdf4;
  border: 1px solid #16a34a;
  border-radius: 8px;
  color: #16a34a;
  font-weight: 600;
  text-align: center;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
`;

const CheckIcon = styled.span`
  font-size: 20px;
`;

interface PlannerFormProps {
  studentName: string;
  content: string;
  onChange: (content: string) => void;
  onSubmit: () => void;
  isSubmitted: boolean;
  isSubmitting: boolean;
}

export const PlannerForm = ({
  studentName,
  content,
  onChange,
  onSubmit,
  isSubmitted,
  isSubmitting,
}: PlannerFormProps) => {
  return (
    <Container>
      <Title>오늘의 플래너</Title>
      <StudentInfo>
        학생: <strong>{studentName}</strong>
      </StudentInfo>

      {isSubmitted && (
        <SuccessMessage>
          <CheckIcon>✓</CheckIcon>
          오늘 플래너 제출 완료
        </SuccessMessage>
      )}

      <FormContainer>
        <Label>오늘 한 공부 내용</Label>
        <Textarea
          value={content}
          onChange={(e) => onChange(e.target.value)}
          placeholder="오늘 공부한 내용을 입력하세요..."
          disabled={isSubmitted}
        />
      </FormContainer>

      <Button
        onClick={onSubmit}
        disabled={!content.trim() || isSubmitted || isSubmitting}
        $fullWidth
      >
        {isSubmitting ? '제출 중...' : isSubmitted ? '제출 완료' : '제출하기'}
      </Button>
    </Container>
  );
};

