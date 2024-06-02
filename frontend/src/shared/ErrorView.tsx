import { Box, Typography } from "@mui/material";

const ErrorView = () => {
    return (
        <Box bgcolor="white">
            <Typography variant="h4">Error</Typography>
            <Typography variant="h6">
            Generic error page, refactor if you want something more specific.variant
            </Typography>
        </Box>

    );
};

export default ErrorView;