import AppBar from '@mui/material/AppBar'
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import { Link, useLocation } from 'react-router-dom'

export default function SearchBar() {
    const location = useLocation();

    const isActive = (path: string) => {
        return location.pathname === path;
    }

    return (
        <Box sx={{flexGrow: 1, marginBottom: 10}}>
        <AppBar position="fixed">
        <Toolbar>
            <Box sx={{display: 'flex', alignItems: 'center'}}>
            <Typography
            variant="h6"
            noWrap
            components={Link}
            to="/"
            sx={{
                marginRight: 4,
                textDecoration: 'none',
                color: 'white',
                borderBottom: isActive('/') ? '4px solid brown' : 'none'
            }}>
        Home
        </Typography>
        <Typography
            variant="h6"
            noWrap
            components={Link}
            to="/"
            sx={{
                marginRight: 4,
                textDecoration: 'none',
                color: 'white',
                borderBottom: isActive('/create') ? '4px solid brown' : 'none'
            }}>Create
                    </Typography>
</Box>
</Toolbar>
</AppBar>
</Box>
    );
}