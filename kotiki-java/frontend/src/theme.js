import {red} from '@mui/material/colors';
import {createTheme} from '@mui/material/styles';

// A custom theme for this app
const theme = createTheme({
  palette: {
    primary: {
      main: '#F4976C',
    },
    secondary: {
      main: '#6c85f4',
    },
    error: {
      main: red.A400,
    },
  },
});

export default theme;
