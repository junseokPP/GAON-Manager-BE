import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Input, Select } from '../components/Input';
import { FormField } from '../components/FormField';
import { ReportAttendanceCard } from '../components/ReportAttendanceCard';
import { ReportWordCard } from '../components/ReportWordCard';
import { ReportPlannerCard } from '../components/ReportPlannerCard';
import { ReportMockExamCard } from '../components/ReportMockExamCard';
import { ReportBehaviorCard } from '../components/ReportBehaviorCard';
import { getMonthlyReport, getMockExamScoresByMonth, getBehaviorRecordsByMonth } from '../mocks/reportMock';

const Container = styled.div`
  max-width: 1400px;
  margin: 0 auto;
  padding: 24px;
`;

const Header = styled.div`
  margin-bottom: 24px;
`;

const Title = styled.h1`
  font-size: 24px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 24px;
`;

const FilterRow = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  margin-bottom: 24px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

const CardsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;

  @media (max-width: 1024px) {
    grid-template-columns: 1fr;
  }
`;

const MonthlyReportPage = () => {
  // Mock 학생 목록
  const mockStudents = [
    { id: 1, name: '홍길동' },
    { id: 2, name: '이영희' },
    { id: 3, name: '김철수' },
  ];

  const [studentId, setStudentId] = useState<number>(mockStudents[0].id);
  const [studentName, setStudentName] = useState<string>(mockStudents[0].name);
  const [month, setMonth] = useState<string>(() => {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
  });
  const [report, setReport] = useState(getMonthlyReport(studentId, studentName, month));
  const [mockExamScores, setMockExamScores] = useState(getMockExamScoresByMonth(studentId, month));
  const [behaviors, setBehaviors] = useState(getBehaviorRecordsByMonth(studentId, month));

  useEffect(() => {
    // 보고서 데이터 로드
    const selectedStudent = mockStudents.find((s) => s.id === studentId);
    if (selectedStudent) {
      setReport(getMonthlyReport(studentId, selectedStudent.name, month));
      setMockExamScores(getMockExamScoresByMonth(studentId, month));
      setBehaviors(getBehaviorRecordsByMonth(studentId, month));
    }
  }, [studentId, month]);

  const handleStudentChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const id = parseInt(e.target.value);
    const selected = mockStudents.find((s) => s.id === id);
    if (selected) {
      setStudentId(id);
      setStudentName(selected.name);
    }
  };

  const handleMonthChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMonth(e.target.value);
  };

  return (
    <Container>
      <Header>
        <Title>월별 보고서</Title>
        <FilterRow>
          <FormField label="학생 선택">
            <Select value={studentId} onChange={handleStudentChange}>
              {mockStudents.map((student) => (
                <option key={student.id} value={student.id}>
                  {student.name}
                </option>
              ))}
            </Select>
          </FormField>
          <FormField label="월 선택">
            <Input
              type="month"
              value={month}
              onChange={handleMonthChange}
            />
          </FormField>
        </FilterRow>
      </Header>
      <CardsGrid>
        <ReportAttendanceCard attendance={report.attendance} />
        <ReportWordCard wordTest={report.wordTest} />
        <ReportPlannerCard planner={report.planner} month={month} />
        <ReportMockExamCard scores={mockExamScores} />
        <ReportBehaviorCard behaviors={behaviors} />
      </CardsGrid>
    </Container>
  );
};

export default MonthlyReportPage;

