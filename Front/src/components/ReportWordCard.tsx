import styled from 'styled-components';
import { Card } from './Card';
import { WordTestSummary } from '../mocks/reportMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const AverageScore = styled.div`
  font-size: 36px;
  font-weight: 700;
  color: #FFC107;
  text-align: center;
  margin-bottom: 24px;
  padding: 24px;
  background-color: #fffbf0;
  border-radius: 8px;
`;

const ScoreList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const ScoreItem = styled.div`
  padding: 12px;
  background-color: #f9fafb;
  border-radius: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ScoreDate = styled.div`
  font-size: 14px;
  color: #6B7280;
`;

const ScoreValue = styled.div<{ $score: number }>`
  font-size: 16px;
  font-weight: 600;
  color: ${({ $score }) => $score >= 40 ? '#16a34a' : $score >= 30 ? '#FFC107' : '#ef4444'};
`;

interface ReportWordCardProps {
  wordTest: WordTestSummary;
}

export const ReportWordCard = ({ wordTest }: ReportWordCardProps) => {
  return (
    <Container>
      <Title>🔹 영단어</Title>
      {wordTest.testCount === 0 ? (
        <div style={{ textAlign: 'center', padding: '40px', color: '#6B7280' }}>
          테스트 기록이 없습니다.
        </div>
      ) : (
        <>
          <AverageScore>
            평균 점수: {wordTest.averageScore}점
          </AverageScore>
          <ScoreList>
            {wordTest.results.map((result, index) => (
              <ScoreItem key={index}>
                <ScoreDate>{result.date}</ScoreDate>
                <ScoreValue $score={result.score}>{result.score}점</ScoreValue>
              </ScoreItem>
            ))}
          </ScoreList>
        </>
      )}
    </Container>
  );
};

