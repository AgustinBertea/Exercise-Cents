import React, { useState } from 'react';
import Axios from "axios";
import { Container, Form, Button, Alert } from 'react-bootstrap';

const Calculator = () => {
  const [num1, setNum1] = useState('');
  const [num2, setNum2] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const response = await Axios.post('localhost:8080/api/v1/calculate', {
         "firstNumber": num1,
         "secondNumber": num2
       });
      setResult(response.data.result);
    } catch (error) {
      setError('The following error has occurred: ' + error.response.data );
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <h1>Calculator</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formNum1">
          <Form.Label>First number</Form.Label>
          <Form.Control
            type="number"
            value={num1}
            onChange={(e) => setNum1(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group controlId="formNum2">
          <Form.Label>Second number</Form.Label>
          <Form.Control
            type="number"
            value={num2}
            onChange={(e) => setNum2(e.target.value)}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit" disabled={loading}>
          {loading ? 'Sending...' : 'Enviar'}
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
