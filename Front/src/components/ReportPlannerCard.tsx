import styled from 'styled-components';
import { Card } from './Card';
import { PlannerSummary } from '../mocks/reportMock';

const Container = styled(Card)`
  padding: 24px;
`;

const Title = styled.h2`
  font-size: 18px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16px;
`;

const SubmissionRate = styled.div`
  font-size: 36px;
  font-weight: 700;
  color: #16a34a;
  text-align: center;
  margin-bottom: 8px;
  padding: 24px;
  background-color: #f0fdf4;
  border-radius: 8px;
`;

const SubmissionInfo = styled.div`
  text-align: center;
  color: #6B7280;
  margin-bottom: 24px;
  font-size: 14px;
`;

const CalendarGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 4px;
  margin-top: 16px;
`;

const CalendarDay = styled.div<{ $isSubmitted?: boolean }>`
  aspect-ratio: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  border-radius: 4px;
  background-color: ${({ $isSubmitted }) => $isSubmitted ? '#16a34a' : '#f3f4f6'};
  color: ${({ $isSubmitted }) => $isSubmitted ? '#FFFFFF' : '#6B7280'};
  font-weight: ${({ $isSubmitted }) => $isSubmitted ? '600' : '400'};
`;

const WeekdayLabel = styled.div`
  text-align: center;
  font-size: 12px;
  color: #6B7280;
  font-weight: 600;
  margin-bottom: 4px;
`;

interface ReportPlannerCardProps {
  planner: PlannerSummary;
  month: string;
}

export const ReportPlannerCard = ({ planner, month }: ReportPlannerCardProps) => {
  // 월의 첫 날과 마지막 날 계산
  const year = parseInt(month.split('-')[0]);
  const monthNum = parseInt(month.split('-')[1]) - 1;
  const firstDay = new Date(year, monthNum, 1);
  const lastDay = new Date(year, monthNum + 1, 0);
  const daysInMonth = lastDay.getDate();
  const startDayOfWeek = firstDay.getDay();

  // 제출된 날짜를 Set으로 변환 (빠른 조회)
  const submittedDatesSet = new Set(planner.submittedDates);

  // 캘린더 날짜 생성
  const calendarDays: Array<{ day: number; isSubmitted: boolean }> = [];
  
  // 빈 칸 (월의 첫 날 이전)
  for (let i = 0; i < startDayOfWeek; i++) {
    calendarDays.push({ day: 0, isSubmitted: false });
  }
  
  // 실제 날짜
  for (let day = 1; day <= daysInMonth; day++) {
    const dateStr = `${year}-${String(monthNum + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    calendarDays.push({
      day,
      isSubmitted: submittedDatesSet.has(dateStr),
    });
  }

  const weekdays = ['일', '월', '화', '수', '목', '금', '토'];

  return (
    <Container>
      <Title>🔹 플래너</Title>
      <SubmissionRate>{planner.submissionRate}%</SubmissionRate>
      <SubmissionInfo>
        {planner.submittedDays} / {planner.totalDays}일 제출
      </SubmissionInfo>
      <div>
        <div style={{ marginBottom: '8px', fontSize: '14px', color: '#6B7280' }}>제출 캘린더</div>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(7, 1fr)', gap: '4px', marginBottom: '4px' }}>
          {weekdays.map((day) => (
            <WeekdayLabel key={day}>{day}</WeekdayLabel>
          ))}
        </div>
        <CalendarGrid>
          {calendarDays.map((item, index) => (
            <CalendarDay key={index} $isSubmitted={item.isSubmitted}>
              {item.day > 0 ? item.day : ''}
            </CalendarDay>
          ))}
        </CalendarGrid>
        <div style={{ marginTop: '12px', fontSize: '12px', color: '#6B7280', display: 'flex', gap: '16px', justifyContent: 'center' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
            <div style={{ width: '12px', height: '12px', backgroundColor: '#16a34a', borderRadius: '2px' }}></div>
            <span>제출</span>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
            <div style={{ width: '12px', height: '12px', backgroundColor: '#f3f4f6', borderRadius: '2px' }}></div>
            <span>미제출</span>
          </div>
        </div>
      </div>
    </Container>
  );
};

