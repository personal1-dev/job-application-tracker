import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Box, Button, Container, Paper, TextField, Typography } from '@mui/material';
import api from '../api/api.js';

export default function Register() {
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const { data } = await api.post('/auth/register', form);
      localStorage.setItem('token', data.token);
      localStorage.setItem('userName', data.name);
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.error || 'Registration failed');
    }
  };

  return (
    <Container maxWidth="sm">
      <Paper sx={{ mt: 8, p: 4 }}>
        <Typography variant="h4" gutterBottom>Register</Typography>
        <Box component="form" onSubmit={submit} sx={{ display: 'grid', gap: 2 }}>
          <TextField label="Name" value={form.name} onChange={e => setForm({ ...form, name: e.target.value })} />
          <TextField label="Email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} />
          <TextField label="Password" type="password" value={form.password} onChange={e => setForm({ ...form, password: e.target.value })} />
          {error && <Typography color="error">{error}</Typography>}
          <Button type="submit" variant="contained">Create Account</Button>
          <Typography>Already have an account? <Link to="/login">Login</Link></Typography>
        </Box>
      </Paper>
    </Container>
  );
}
