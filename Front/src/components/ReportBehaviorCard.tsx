import styled from 'styled-components';
import { Card } from './Card';
import { BehaviorRecord } from '../mocks/behaviorMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const BehaviorList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

const BehaviorItem = styled.div`
  padding: 16px;
  background-color: #f9fafb;
  border-radius: 8px;
  border-left: 4px solid #FFC107;
`;

const BehaviorHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
`;

const BehaviorType = styled.span<{ $type: string }>`
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
  background-color: ${({ $type }) => {
    switch ($type) {
      case '수면':
        return '#dbeafe';
      case '태도':
        return '#fef3c7';
      case '태블릿':
        return '#e0e7ff';
      default:
        return '#f3f4f6';
    }
  }};
  color: ${({ $type }) => {
    switch ($type) {
      case '수면':
        return '#1e40af';
      case '태도':
        return '#92400e';
      case '태블릿':
        return '#4338ca';
      default:
        return '#6b7280';
    }
  }};
`;

const BehaviorDate = styled.div`
  font-size: 12px;
  color: #6B7280;
`;

const BehaviorDetail = styled.div`
  font-size: 14px;
  color: #333333;
  line-height: 1.5;
`;

const BehaviorAuthor = styled.div`
  font-size: 12px;
  color: #6B7280;
  margin-top: 8px;
`;

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  });
};

interface ReportBehaviorCardProps {
  behaviors: BehaviorRecord[];
}

export const ReportBehaviorCard = ({ behaviors }: ReportBehaviorCardProps) => {
  if (behaviors.length === 0) {
    return (
      <Container>
        <Title>🔹 학습 태도</Title>
        <div style={{ textAlign: 'center', padding: '40px', color: '#6B7280' }}>
          기록이 없습니다.
        </div>
      </Container>
    );
  }

  return (
    <Container>
      <Title>🔹 학습 태도</Title>
      <BehaviorList>
        {behaviors.map((behavior) => (
          <BehaviorItem key={behavior.id}>
            <BehaviorHeader>
              <BehaviorType $type={behavior.type}>{behavior.type}</BehaviorType>
              <BehaviorDate>{formatDate(behavior.createdAt)}</BehaviorDate>
            </BehaviorHeader>
            <BehaviorDetail>{behavior.detail}</BehaviorDetail>
            <BehaviorAuthor>작성자: {behavior.createdBy}</BehaviorAuthor>
          </BehaviorItem>
        ))}
      </BehaviorList>
    </Container>
  );
};

