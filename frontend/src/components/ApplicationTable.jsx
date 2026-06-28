import { Button, Chip, Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material';

export default function ApplicationTable({ applications, onDelete }) {
  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>Company</TableCell>
          <TableCell>Role</TableCell>
          <TableCell>Status</TableCell>
          <TableCell>Applied</TableCell>
          <TableCell>Follow-up</TableCell>
          <TableCell>Link</TableCell>
          <TableCell>Action</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {applications.map(app => (
          <TableRow key={app.id}>
            <TableCell>{app.companyName}</TableCell>
            <TableCell>{app.jobTitle}</TableCell>
            <TableCell><Chip label={app.status} /></TableCell>
            <TableCell>{app.appliedDate}</TableCell>
            <TableCell>{app.followUpDate || '-'}</TableCell>
            <TableCell>{app.jobLink ? <a href={app.jobLink} target="_blank">Open</a> : '-'}</TableCell>
            <TableCell><Button color="error" onClick={() => onDelete(app.id)}>Delete</Button></TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
