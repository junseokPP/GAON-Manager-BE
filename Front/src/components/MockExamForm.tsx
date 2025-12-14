import styled from 'styled-components';
import { Card } from './Card';
import { Button } from './Button';
import { Input, Select } from './Input';
import { FormField } from './FormField';
import { MockExamType } from '../mocks/mockExamMock';

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

const ScoreGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

const NumberInput = styled(Input)`
  &::-webkit-inner-spin-button,
  &::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  -moz-appearance: textfield;
`;

interface MockExamFormProps {
  studentId: number;
  studentName: string;
  examType: MockExamType | '';
  korean: number;
  english: number;
  math: number;
  science1: number;
  science2: number;
  onStudentChange: (studentId: number, studentName: string) => void;
  onExamTypeChange: (type: MockExamType) => void;
  onScoreChange: (field: 'korean' | 'english' | 'math' | 'science1' | 'science2', value: number) => void;
  onSubmit: () => void;
  isSubmitting: boolean;
  students: Array<{ id: number; name: string }>;
}

export const MockExamForm = ({
  studentId,
  studentName,
  examType,
  korean,
  english,
  math,
  science1,
  science2,
  onStudentChange,
  onExamTypeChange,
  onScoreChange,
  onSubmit,
  isSubmitting,
  students,
}: MockExamFormProps) => {
  const handleStudentSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(e.target.value);
    const selected = students.find((s) => s.id === selectedId);
    if (selected) {
      onStudentChange(selectedId, selected.name);
    }
  };

  const handleScoreChange = (field: 'korean' | 'english' | 'math' | 'science1' | 'science2') => 
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = parseInt(e.target.value) || 0;
      const clampedValue = Math.max(0, Math.min(100, value));
      onScoreChange(field, clampedValue);
    };

  return (
    <Container>
      <Title>모의고사 성적 입력</Title>
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
        <FormField label="모의고사 종류" required>
          <Select
            value={examType}
            onChange={(e) => onExamTypeChange(e.target.value as MockExamType)}
          >
            <option value="">종류를 선택하세요</option>
            <option value="3월 모평">3월 모평</option>
            <option value="6월 모평">6월 모평</option>
            <option value="9월 모평">9월 모평</option>
            <option value="수능">수능</option>
          </Select>
        </FormField>
      </FormRow>
      <ScoreGrid>
        <FormField label="국어" required>
          <NumberInput
            type="number"
            min="0"
            max="100"
            value={korean || ''}
            onChange={handleScoreChange('korean')}
            placeholder="0-100"
          />
        </FormField>
        <FormField label="영어" required>
          <NumberInput
            type="number"
            min="0"
            max="100"
            value={english || ''}
            onChange={handleScoreChange('english')}
            placeholder="0-100"
          />
        </FormField>
        <FormField label="수학" required>
          <NumberInput
            type="number"
            min="0"
            max="100"
            value={math || ''}
            onChange={handleScoreChange('math')}
            placeholder="0-100"
          />
        </FormField>
        <FormField label="탐구1" required>
          <NumberInput
            type="number"
            min="0"
            max="100"
            value={science1 || ''}
            onChange={handleScoreChange('science1')}
            placeholder="0-100"
          />
        </FormField>
        <FormField label="탐구2" required>
          <NumberInput
            type="number"
            min="0"
            max="100"
            value={science2 || ''}
            onChange={handleScoreChange('science2')}
            placeholder="0-100"
          />
        </FormField>
      </ScoreGrid>
      <Button
        onClick={onSubmit}
        disabled={!studentId || !examType || isSubmitting}
        $fullWidth
        style={{ marginTop: '16px' }}
      >
        {isSubmitting ? '저장 중...' : '저장'}
      </Button>
    </Container>
  );
};

