import '../stylesheets/Calculator.css';
import React, { useState, useEffect } from 'react';
import Axios from "axios";
import { Form, Button, Alert } from 'react-bootstrap';

function Calculator() {

  // Visual
  const [isSmallScreen, setIsSmallScreen] = useState(false);
  const smallScreenClasses = "full-height card shadow calculator-form col-xxl-4 col-xl-5 col-lg-6 col-md-6 col-sm-7 col-12 d-sm-none";
  const otherSizeScreenClasses = "card shadow calculator-form col-xxl-4 col-xl-5 col-lg-6 col-md-6 col-sm-7 col-12";

  useEffect(() => {
    function handleResize() {
      console.log("TAMAÑO: " + window.innerWidth)
      console.log("PROPIEDAD: " + window.innerWidth < 576)
      setIsSmallScreen(window.innerWidth < 576);
    }
    handleResize();
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  // Functionality
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

    Axios.post('http://localhost:8080/api/v1/calculate', null, {
      params: {
        firstNumber: parseFloat(firstNumber),
        secondNumber: parseFloat(secondNumber)
      }
    })
    .then((response) => {
      setLoading(false);
      setResult(response.data);
    })
    .catch((error) => {
      setLoading(false);
      if (error.response) {
        setError('The following error has occurred: ' + error.response.data.errorMessage);
      } else if (error.request) {
        setError('Network Error: No response received from server');
      } else {
        setError(`Request Error: ${error.message || 'An unknown error occurred'}`);
  }

    });
  }

  return (
    <div className="calculator-container">
    <div className={isSmallScreen ? smallScreenClasses : otherSizeScreenClasses}>
      <Form onSubmit={handleSubmit} >
      <center><h2><b>Calculator</b></h2></center>
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
        <Button variant="primary" type="submit" disabled={loading} className="btn btn-primary send-button w-100">
          {loading ? 'Sending...' : 'Send'}
        </Button>
        {result !== null && (
          <center>
          <Alert variant="info" className="mt-3">
            Result: {result}
          </Alert>
          </center>
        )}
        {error && (
          <center>
          <Alert variant="danger" className="mt-3">
            {error}
          </Alert>
          </center>
        )}
      </Form>
      </div>


    </div>
  );
}

export default Calculator;
