import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AppBar, Box, Button, Container, Grid, Paper, TextField, Toolbar, Typography } from '@mui/material';
import api from '../api/api.js';
import ApplicationForm from '../components/ApplicationForm.jsx';
import ApplicationTable from '../components/ApplicationTable.jsx';

export default function Dashboard() {
  const [applications, setApplications] = useState([]);
  const [dashboard, setDashboard] = useState({ total: 0, applied: 0, interview: 0, offer: 0, rejected: 0 });
  const [keyword, setKeyword] = useState('');
  const navigate = useNavigate();

  const loadData = async () => {
    const [appsRes, dashRes] = await Promise.all([
      api.get('/applications'),
      api.get('/applications/dashboard')
    ]);
    setApplications(appsRes.data);
    setDashboard(dashRes.data);
  };

  useEffect(() => { loadData(); }, []);

  const saveApplication = async (payload) => {
    await api.post('/applications', payload);
    await loadData();
  };

  const deleteApplication = async (id) => {
    await api.delete(`/applications/${id}`);
    await loadData();
  };

  const search = async () => {
    if (!keyword.trim()) return loadData();
    const { data } = await api.get(`/applications/search?keyword=${encodeURIComponent(keyword)}`);
    setApplications(data);
  };

  const logout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>Job Application Tracker</Typography>
          <Button color="inherit" onClick={logout}>Logout</Button>
        </Toolbar>
      </AppBar>
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Grid container spacing={2} sx={{ mb: 3 }}>
          {Object.entries(dashboard).map(([key, value]) => (
            <Grid item xs={6} md={2.4} key={key}>
              <Paper sx={{ p: 2, textAlign: 'center' }}>
                <Typography variant="h5">{value}</Typography>
                <Typography sx={{ textTransform: 'capitalize' }}>{key}</Typography>
              </Paper>
            </Grid>
          ))}
        </Grid>

        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h5" gutterBottom>Add Job Application</Typography>
          <ApplicationForm onSave={saveApplication} />
        </Paper>

        <Paper sx={{ p: 3 }}>
          <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
            <TextField label="Search company or role" value={keyword} onChange={e => setKeyword(e.target.value)} />
            <Button variant="outlined" onClick={search}>Search</Button>
            <Button onClick={loadData}>Reset</Button>
          </Box>
          <ApplicationTable applications={applications} onDelete={deleteApplication} />
        </Paper>
      </Container>
    </>
  );
}
