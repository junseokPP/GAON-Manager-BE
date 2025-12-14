import styled from 'styled-components';
import { Card } from './Card';
import { Table, Thead, Th, Td, Tr } from './Table';
import { BehaviorRecord } from '../mocks/behaviorMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const TypeBadge = styled.span<{ $type: string }>`
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

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  });
};

interface BehaviorListProps {
  records: BehaviorRecord[];
}

export const BehaviorList = ({ records }: BehaviorListProps) => {
  if (records.length === 0) {
    return (
      <Container>
        <Title>학습 태도 기록</Title>
        <div style={{ textAlign: 'center', padding: '40px', color: '#6B7280' }}>
          기록이 없습니다.
        </div>
      </Container>
    );
  }

  return (
    <Container>
      <Title>학습 태도 기록</Title>
      <Table>
        <Thead>
          <tr>
            <Th>학생명</Th>
            <Th>유형</Th>
            <Th>상세 내용</Th>
            <Th>작성자</Th>
            <Th>작성일시</Th>
          </tr>
        </Thead>
        <tbody>
          {records.map((record) => (
            <Tr key={record.id}>
              <Td>{record.studentName}</Td>
              <Td>
                <TypeBadge $type={record.type}>{record.type}</TypeBadge>
              </Td>
              <Td>{record.detail}</Td>
              <Td>{record.createdBy}</Td>
              <Td>{formatDate(record.createdAt)}</Td>
            </Tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

