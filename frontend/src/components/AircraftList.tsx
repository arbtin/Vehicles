function ListComponent({rows}) {
    return (
        <>
        <h1>Aircraft</h1>
        <TableContainer component={Paper}>
            <Table sx={{minWidth: 650}} aria-label="simple-table">
            <TableHead>
            <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Airframe</TableCell>
                        <TableCell>Pilot</TableCell>
                        </TableRow>
                        </TableHead>
                        <TableBody>
                        {rows.map((row) => (
                        <TableRow
                        key={row.name}
                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                        >
                        <TableCell component="th" scope="row">
                        {row.name}
                        </TableCell>
                 <TableCell>{row.airframe}</TableCell>
            <TableCell>{row.pilot}</TableCell>
                    <TableCell><button>Delete</button></TableCell>
</TableRow>
  ))}
  </TableBody>
  </Table>
  </TableContainer>
  </>
    )
}

export default ListComponent;