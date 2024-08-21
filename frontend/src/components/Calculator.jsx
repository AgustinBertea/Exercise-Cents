import React, { useState } from 'react';
import axios from "axios";
import { Container, Form, Button, Alert } from 'react-bootstrap';

const Calculator = () => {
  const [firstNumber, setFirstNumber] = useState('');
  const [secondNumber, setSecondNumber] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const response = await axios.post('http://localhost:8080/api/v1/calculate', null, {
        params: {
          firstNumber: parseFloat(firstNumber),
          secondNumber: parseFloat(secondNumber)
        }
      });
      setResult(response.data);
    } catch (error) {
      setError('The following error has occurred: ' + error.response.data.errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <h1>Calculator</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formFirstNumber">
          <Form.Label>First number</Form.Label>
          <Form.Control
            type="number"
            value={firstNumber}
            onChange={(e) => setFirstNumber(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group controlId="formSecondNumber">
          <Form.Label>Second number</Form.Label>
          <Form.Control
            type="number"
            value={secondNumber}
            onChange={(e) => setSecondNumber(e.target.value)}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit" disabled={loading}>
          {loading ? 'Sending...' : 'Send'}
        </Button>
      </Form>
      {result !== null && (
        <Alert variant="success" className="mt-3">
          Result: {result}
        </Alert>
      )}
      {error && (
        <Alert variant="danger" className="mt-3">
          {error}
        </Alert>
      )}
    </Container>
  );
};

export default Calculator;
