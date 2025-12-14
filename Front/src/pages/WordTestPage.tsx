import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { WordTestCard } from '../components/WordTestCard';
import { 
  mockWordTestQuestions, 
  getWordTestHistory, 
  saveWordTestResult, 
  generateRandomScore,
  WordTestResult 
} from '../mocks/wordMock';

const Container = styled.div`
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
`;

const WordTestPage = () => {
  // Mock 학생 ID (실제로는 로그인한 학생 정보에서 가져옴)
  const studentId = 1;
  const studentName = '홍길동';

  const [isTestActive, setIsTestActive] = useState(false);
  const [selectedAnswers, setSelectedAnswers] = useState<Map<number, number>>(new Map());
  const [result, setResult] = useState<WordTestResult | null>(null);
  const [history, setHistory] = useState<WordTestResult[]>([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    // 히스토리 로드
    const testHistory = getWordTestHistory(studentId);
    setHistory(testHistory);
  }, []);

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

  return (
    <Container>
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
    </Container>
  );
};

export default WordTestPage;

