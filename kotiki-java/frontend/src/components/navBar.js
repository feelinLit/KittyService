import {AppBar, Box, Button, Link, SvgIcon, Toolbar, Typography} from "@mui/material";
import {ReactComponent as Logo} from "./cat_logo.svg";

export const NavBar = (props) => {
  return (
    <AppBar position={'sticky'}>
      <Box sx={{flexGrow: 1}}>
        <Toolbar>
          <Link href="/">
            <SvgIcon component={Logo} inheritViewBox sx={{fontSize: 50, mt: 1}}/>
          </Link>
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            color="black"
            sx={{textDecoration: 'none'}}
          >
            KittyService
          </Typography>
          <Box sx={{flexGrow: 1}}/>
          {
            props.userName ? (
                <Typography
                  noWrap
                  mr={3}
                  color="black"
                >
                  Logged in as: <strong>{props.userName}</strong>
                </Typography>
              ) :
              null
          }
          {
            props.isLoggedIn === false ? (
              <Button onClick={props.onOpenLogInForm} variant="contained" color={"secondary"} size="large">
                Log in
              </Button>
            ) : (
              <Button onClick={props.onLogOut} variant="contained" color={"secondary"} size="large">
                Log out
              </Button>
            )
          }
        </Toolbar>
      </Box>
    </AppBar>
  )
}