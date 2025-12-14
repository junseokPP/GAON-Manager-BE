import styled from 'styled-components';
import { Card } from './Card';
import { Button } from './Button';
import { WordTestQuestion, WordTestResult } from '../mocks/wordMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const QuestionContainer = styled.div`
  margin-bottom: 24px;
`;

const QuestionTitle = styled.div`
  font-size: 16px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 12px;
`;

const WordDisplay = styled.div`
  font-size: 24px;
  font-weight: 700;
  color: #FFC107;
  margin-bottom: 8px;
`;

const MeaningDisplay = styled.div`
  font-size: 14px;
  color: #6B7280;
  margin-bottom: 16px;
`;

const OptionsList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const OptionButton = styled.button<{ $selected?: boolean; $correct?: boolean; $wrong?: boolean }>`
  padding: 12px 16px;
  border: 2px solid ${({ $selected, $correct, $wrong }) => 
    $correct ? '#16a34a' : 
    $wrong ? '#ef4444' : 
    $selected ? '#FFC107' : '#E0E0E0'};
  border-radius: 8px;
  background-color: ${({ $selected, $correct, $wrong }) => 
    $correct ? '#f0fdf4' : 
    $wrong ? '#fef2f2' : 
    $selected ? '#fff7e6' : '#FFFFFF'};
  color: #333333;
  font-size: 14px;
  text-align: left;
  cursor: pointer;
  transition: all 0.2s;

  &:hover:not(:disabled) {
    border-color: ${({ $selected }) => $selected ? '#FFC107' : '#FFC107'};
    background-color: ${({ $selected }) => $selected ? '#fff7e6' : '#fffbf0'};
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.7;
  }
`;

const ResultContainer = styled.div`
  padding: 24px;
  background-color: #f9fafb;
  border-radius: 8px;
  text-align: center;
`;

const ScoreDisplay = styled.div`
  font-size: 48px;
  font-weight: 700;
  color: #FFC107;
  margin-bottom: 8px;
`;

const ScoreLabel = styled.div`
  font-size: 16px;
  color: #6B7280;
  margin-bottom: 16px;
`;

const HistoryContainer = styled.div`
  margin-top: 24px;
`;

const HistoryTitle = styled.h3`
  font-size: 16px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 12px;
`;

const HistoryList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const HistoryItem = styled.div`
  padding: 12px;
  background-color: #FFFFFF;
  border: 1px solid #E0E0E0;
  border-radius: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const HistoryDate = styled.div`
  font-size: 14px;
  color: #6B7280;
`;

const HistoryScore = styled.div<{ $score: number }>`
  font-size: 16px;
  font-weight: 600;
  color: ${({ $score }) => $score >= 40 ? '#16a34a' : $score >= 30 ? '#FFC107' : '#ef4444'};
`;

interface WordTestCardProps {
  studentName: string;
  questions: WordTestQuestion[];
  selectedAnswers: Map<number, number>;
  onAnswerSelect: (questionId: number, answerIndex: number) => void;
  onSubmit: () => void;
  result: WordTestResult | null;
  history: WordTestResult[];
  onStartTest: () => void;
  isTestActive: boolean;
  isSubmitting: boolean;
}

export const WordTestCard = ({
  studentName,
  questions,
  selectedAnswers,
  onAnswerSelect,
  onSubmit,
  result,
  history,
  onStartTest,
  isTestActive,
  isSubmitting,
}: WordTestCardProps) => {
  if (!isTestActive && !result) {
    return (
      <Container>
        <Title>영단어 테스트</Title>
        <div style={{ marginBottom: '16px', color: '#6B7280' }}>
          학생: <strong>{studentName}</strong>
        </div>
        <Button onClick={onStartTest} style={{ width: '100%' }}>
          단어 테스트 시작하기
        </Button>
        {history.length > 0 && (
          <HistoryContainer>
            <HistoryTitle>점수 히스토리</HistoryTitle>
            <HistoryList>
              {history.slice(0, 5).map((h) => (
                <HistoryItem key={h.id}>
                  <HistoryDate>{h.date}</HistoryDate>
                  <HistoryScore $score={h.score}>{h.score}점</HistoryScore>
                </HistoryItem>
              ))}
            </HistoryList>
          </HistoryContainer>
        )}
      </Container>
    );
  }

  if (result) {
    return (
      <Container>
        <Title>테스트 결과</Title>
        <ResultContainer>
          <ScoreDisplay>{result.score}점</ScoreDisplay>
          <ScoreLabel>
            {result.correctAnswers} / {result.totalQuestions} 정답
          </ScoreLabel>
          <Button onClick={onStartTest} style={{ marginTop: '16px' }}>
            다시 테스트하기
          </Button>
        </ResultContainer>
        {history.length > 0 && (
          <HistoryContainer>
            <HistoryTitle>점수 히스토리</HistoryTitle>
            <HistoryList>
              {history.slice(0, 5).map((h) => (
                <HistoryItem key={h.id}>
                  <HistoryDate>{h.date}</HistoryDate>
                  <HistoryScore $score={h.score}>{h.score}점</HistoryScore>
                </HistoryItem>
              ))}
            </HistoryList>
          </HistoryContainer>
        )}
      </Container>
    );
  }

  return (
    <Container>
      <Title>영단어 테스트</Title>
      <div style={{ marginBottom: '24px', color: '#6B7280' }}>
        학생: <strong>{studentName}</strong>
      </div>
      {questions.map((question, index) => {
        const selected = selectedAnswers.get(question.id);
        return (
          <QuestionContainer key={question.id}>
            <QuestionTitle>문제 {index + 1}</QuestionTitle>
            <WordDisplay>{question.word}</WordDisplay>
            <MeaningDisplay>의미: {question.meaning}</MeaningDisplay>
            <OptionsList>
              {question.options.map((option, optIndex) => (
                <OptionButton
                  key={optIndex}
                  onClick={() => onAnswerSelect(question.id, optIndex)}
                  disabled={isSubmitting}
                  $selected={selected === optIndex}
                >
                  {option}
                </OptionButton>
              ))}
            </OptionsList>
          </QuestionContainer>
        );
      })}
      <Button 
        onClick={onSubmit} 
        disabled={selectedAnswers.size < questions.length || isSubmitting}
        style={{ width: '100%', marginTop: '24px' }}
      >
        {isSubmitting ? '제출 중...' : '제출하기'}
      </Button>
    </Container>
  );
};

