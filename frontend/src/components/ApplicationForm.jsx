import { useState } from 'react';
import { Box, Button, MenuItem, TextField } from '@mui/material';

const emptyForm = {
  companyName: '', jobTitle: '', location: '', status: 'APPLIED',
  appliedDate: new Date().toISOString().slice(0, 10), followUpDate: '', jobLink: '', notes: ''
};

export default function ApplicationForm({ onSave }) {
  const [form, setForm] = useState(emptyForm);

  const submit = async (e) => {
    e.preventDefault();
    await onSave({ ...form, followUpDate: form.followUpDate || null });
    setForm(emptyForm);
  };

  return (
    <Box component="form" onSubmit={submit} sx={{ display: 'grid', gridTemplateColumns: { md: '1fr 1fr' }, gap: 2 }}>
      <TextField required label="Company" value={form.companyName} onChange={e => setForm({ ...form, companyName: e.target.value })} />
      <TextField required label="Job Title" value={form.jobTitle} onChange={e => setForm({ ...form, jobTitle: e.target.value })} />
      <TextField label="Location" value={form.location} onChange={e => setForm({ ...form, location: e.target.value })} />
      <TextField select label="Status" value={form.status} onChange={e => setForm({ ...form, status: e.target.value })}>
        <MenuItem value="APPLIED">Applied</MenuItem>
        <MenuItem value="INTERVIEW">Interview</MenuItem>
        <MenuItem value="OFFER">Offer</MenuItem>
        <MenuItem value="REJECTED">Rejected</MenuItem>
      </TextField>
      <TextField type="date" label="Applied Date" InputLabelProps={{ shrink: true }} value={form.appliedDate} onChange={e => setForm({ ...form, appliedDate: e.target.value })} />
      <TextField type="date" label="Follow-up Date" InputLabelProps={{ shrink: true }} value={form.followUpDate} onChange={e => setForm({ ...form, followUpDate: e.target.value })} />
      <TextField label="Job Link" value={form.jobLink} onChange={e => setForm({ ...form, jobLink: e.target.value })} />
      <TextField label="Notes" value={form.notes} onChange={e => setForm({ ...form, notes: e.target.value })} />
      <Button type="submit" variant="contained">Add Application</Button>
    </Box>
  );
}
