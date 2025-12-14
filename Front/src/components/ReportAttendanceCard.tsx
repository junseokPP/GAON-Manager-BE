import styled from 'styled-components';
import { Card } from './Card';
import { AttendanceSummary } from '../mocks/reportMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const StatGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

const StatItem = styled.div`
  padding: 16px;
  background-color: #f9fafb;
  border-radius: 8px;
`;

const StatLabel = styled.div`
  font-size: 14px;
  color: #6B7280;
  margin-bottom: 8px;
`;

const StatValue = styled.div`
  font-size: 24px;
  font-weight: 700;
  color: #333333;
`;

interface ReportAttendanceCardProps {
  attendance: AttendanceSummary;
}

export const ReportAttendanceCard = ({ attendance }: ReportAttendanceCardProps) => {
  return (
    <Container>
      <Title>🔹 출결 요약</Title>
      <StatGrid>
        <StatItem>
          <StatLabel>총 등원일</StatLabel>
          <StatValue>{attendance.totalDays}일</StatValue>
        </StatItem>
        <StatItem>
          <StatLabel>무단지각 횟수</StatLabel>
          <StatValue style={{ color: '#facc15' }}>{attendance.unexcusedLateCount}회</StatValue>
        </StatItem>
        <StatItem>
          <StatLabel>무단결석 횟수</StatLabel>
          <StatValue style={{ color: '#ef4444' }}>{attendance.unexcusedAbsentCount}회</StatValue>
        </StatItem>
        <StatItem>
          <StatLabel>총 순공시간</StatLabel>
          <StatValue>{attendance.totalStudyHours}</StatValue>
        </StatItem>
      </StatGrid>
    </Container>
  );
};

