import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { WordTestCard } from '../components/WordTestCard';
import { PlannerForm } from '../components/PlannerForm';
import { 
  mockWordTestQuestions, 
  getWordTestHistory, 
  saveWordTestResult, 
  generateRandomScore,
  WordTestResult 
} from '../mocks/wordMock';
import { 
  getTodayPlannerStatus, 
  savePlannerSubmission 
} from '../mocks/plannerMock';

const Container = styled.div`
  max-width: 1400px;
  margin: 0 auto;
  padding: 24px;
`;

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;

  @media (max-width: 1024px) {
    grid-template-columns: 1fr;
  }
`;

const StudentStudyPage = () => {
  // Mock 학생 ID (실제로는 로그인한 학생 정보에서 가져옴)
  const studentId = 1;
  const studentName = '홍길동';

  // 영단어 테스트 상태
  const [isTestActive, setIsTestActive] = useState(false);
  const [selectedAnswers, setSelectedAnswers] = useState<Map<number, number>>(new Map());
  const [result, setResult] = useState<WordTestResult | null>(null);
  const [history, setHistory] = useState<WordTestResult[]>([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  // 플래너 상태
  const [content, setContent] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [isSubmittingPlanner, setIsSubmittingPlanner] = useState(false);

  useEffect(() => {
    // 히스토리 로드
    const testHistory = getWordTestHistory(studentId);
    setHistory(testHistory);
    
    // 오늘 제출 여부 확인
    const submitted = getTodayPlannerStatus(studentId);
    setIsSubmitted(submitted);
  }, []);

  // 영단어 테스트 핸들러
  const handleStartTest = () => {
    setIsTestActive(true);
    setResult(null);
    setSelectedAnswers(new Map());
  };

  const handleAnswerSelect = (questionId: number, answerIndex: number) => {
    setSelectedAnswers((prev) => {
      const newMap = new Map(prev);
      newMap.set(questionId, answerIndex);
      return newMap;
    });
  };

  const handleSubmit = () => {
    setIsSubmitting(true);
    
    // 정답 확인
    let correctCount = 0;
    mockWordTestQuestions.forEach((question) => {
      const selected = selectedAnswers.get(question.id);
      if (selected === question.correctAnswer) {
        correctCount++;
      }
    });

    // 점수 생성
    const score = generateRandomScore(correctCount);
    
    // 결과 생성
    const testResult: WordTestResult = {
      id: Date.now(),
      date: new Date().toISOString().split('T')[0],
      score,
      totalQuestions: mockWordTestQuestions.length,
      correctAnswers: correctCount,
    };

    // 저장
    saveWordTestResult(studentId, testResult);
    
    // 상태 업데이트
    setTimeout(() => {
      setResult(testResult);
      setIsTestActive(false);
      setIsSubmitting(false);
      setHistory(getWordTestHistory(studentId));
    }, 500);
  };

  // 플래너 핸들러
  const handlePlannerSubmit = () => {
    if (!content.trim()) {
      return;
    }

    setIsSubmittingPlanner(true);

    // 제출 저장
    setTimeout(() => {
      savePlannerSubmission(studentId, content);
      setIsSubmitted(true);
      setIsSubmittingPlanner(false);
    }, 500);
  };

  return (
    <Container>
      <GridContainer>
        <WordTestCard
          studentName={studentName}
          questions={mockWordTestQuestions}
          selectedAnswers={selectedAnswers}
          onAnswerSelect={handleAnswerSelect}
          onSubmit={handleSubmit}
          result={result}
          history={history}
          onStartTest={handleStartTest}
          isTestActive={isTestActive}
          isSubmitting={isSubmitting}
        />
        <PlannerForm
          studentName={studentName}
          content={content}
          onChange={setContent}
          onSubmit={handlePlannerSubmit}
          isSubmitted={isSubmitted}
          isSubmitting={isSubmittingPlanner}
        />
      </GridContainer>
    </Container>
  );
};

export default StudentStudyPage;

