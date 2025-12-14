import styled from 'styled-components';
import { Card } from './Card';
import { Button } from './Button';
import { Input, Select, Textarea } from './Input';
import { FormField } from './FormField';
import { BehaviorType } from '../mocks/behaviorMock';

const Container = styled(Card)`
  padding: 24px;
  margin-bottom: 24px;
`;

const Title = styled.h2`
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 24px;
`;

const FormRow = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

interface BehaviorFormProps {
  studentId: number;
  studentName: string;
  type: BehaviorType | '';
  detail: string;
  onStudentChange: (studentId: number, studentName: string) => void;
  onTypeChange: (type: BehaviorType) => void;
  onDetailChange: (detail: string) => void;
  onSubmit: () => void;
  isSubmitting: boolean;
  students: Array<{ id: number; name: string }>;
}

export const BehaviorForm = ({
  studentId,
  studentName,
  type,
  detail,
  onStudentChange,
  onTypeChange,
  onDetailChange,
  onSubmit,
  isSubmitting,
  students,
}: BehaviorFormProps) => {
  const handleStudentSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(e.target.value);
    const selected = students.find((s) => s.id === selectedId);
    if (selected) {
      onStudentChange(selectedId, selected.name);
    }
  };

  return (
    <Container>
      <Title>학습 태도 작성</Title>
      <FormRow>
        <FormField label="학생 선택" required>
          <Select value={studentId || ''} onChange={handleStudentSelect}>
            <option value="">학생을 선택하세요</option>
            {students.map((student) => (
              <option key={student.id} value={student.id}>
                {student.name}
              </option>
            ))}
          </Select>
        </FormField>
        <FormField label="태도 유형" required>
          <Select
            value={type}
            onChange={(e) => onTypeChange(e.target.value as BehaviorType)}
          >
            <option value="">유형을 선택하세요</option>
            <option value="수면">수면</option>
            <option value="태도">태도</option>
            <option value="태블릿">태블릿</option>
          </Select>
        </FormField>
      </FormRow>
      <FormField label="상세 내용" required>
        <Textarea
          value={detail}
          onChange={(e) => onDetailChange(e.target.value)}
          placeholder="학습 태도에 대한 상세 내용을 입력하세요..."
          style={{ minHeight: '120px' }}
        />
      </FormField>
      <Button
        onClick={onSubmit}
        disabled={!studentId || !type || !detail.trim() || isSubmitting}
        $fullWidth
        style={{ marginTop: '16px' }}
      >
        {isSubmitting ? '저장 중...' : '저장'}
      </Button>
    </Container>
  );
};

