import styled from 'styled-components';
import { Card } from './Card';
import { Table, Thead, Th, Td, Tr } from './Table';
import { MockExamScore } from '../mocks/mockExamMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const TotalScore = styled.span`
  font-weight: 700;
  color: #FFC107;
`;

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  });
};

interface MockExamTableProps {
  scores: MockExamScore[];
}

export const MockExamTable = ({ scores }: MockExamTableProps) => {
  if (scores.length === 0) {
    return (
      <Container>
        <Title>모의고사 성적 기록</Title>
        <div style={{ textAlign: 'center', padding: '40px', color: '#6B7280' }}>
          기록이 없습니다.
        </div>
      </Container>
    );
  }

  return (
    <Container>
      <Title>모의고사 성적 기록</Title>
      <Table>
        <Thead>
          <tr>
            <Th>학생명</Th>
            <Th>시험 종류</Th>
            <Th>국어</Th>
            <Th>영어</Th>
            <Th>수학</Th>
            <Th>탐구1</Th>
            <Th>탐구2</Th>
            <Th>총점</Th>
            <Th>날짜</Th>
          </tr>
        </Thead>
        <tbody>
          {scores.map((score) => (
            <Tr key={score.id}>
              <Td>{score.studentName}</Td>
              <Td>{score.examType}</Td>
              <Td>{score.korean}</Td>
              <Td>{score.english}</Td>
              <Td>{score.math}</Td>
              <Td>{score.science1}</Td>
              <Td>{score.science2}</Td>
              <Td>
                <TotalScore>{score.totalScore}</TotalScore>
              </Td>
              <Td>{formatDate(score.date)}</Td>
            </Tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

