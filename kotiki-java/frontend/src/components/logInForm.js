import {Alert, Box, Button, Collapse, CssBaseline, Grid, Snackbar, TextField, Typography} from "@mui/material";
import React from "react";

class LogInForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {catImg: null, disabled: true, username: '', password: ''};
  }

  componentDidMount() {
    fetch('https://aws.random.cat/meow')
      .then(res => res.json())
      .then(data => {
        const img = new Image();
        img.src = data.file;
        img.onload = () => this.setState({catImg: img});
      });
  }

  render() {
    const {forwardedRef, ...props} = this.props;
    let username = this.state.username, password = this.state.password;

    const handleChangeUsername = (e) => {
      const username = e.target.value;
      if (username.trim() !== '' && password.trim() !== '')
        this.setState({disabled: false, username: username});
      else
        this.setState({disabled: true, username: username});
    };

    const handleChangePassword = (e) => {
      const password = e.target.value;
      if (username.trim() !== '' && password.trim() !== '')
        this.setState({disabled: false, password: password});
      else
        this.setState({disabled: true, password: password});

    };

    return (
      <Grid container {...props} ref={forwardedRef} spacing={4} padding={0}>
        <CssBaseline/>
        {
          this.state.catImg != null ? (
            <Grid
              item
              xs={false}
              sm={4}
              md={7}
              sx={{
                backgroundImage: `url(${this.state.catImg.src})`,
                backgroundRepeat: 'no-repeat',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
              }}
            />
          ) : (
            <Grid
              item
              xs={false}
              sm={4}
              md={7}
              className={'App-loading'}
            />
          )
        }

        <Grid item xs={12} sm={8} md={5}>
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              mr: 4,
            }}
          >
            <Typography component="h1" variant="h5" align="center">
              Log in
            </Typography>
            <Box component="form" onSubmit={(e) => props.onSubmit(e, username, password)} sx={{mt: 1}}>
              <Collapse in={props.authfailed}>
                <Alert severity="error">Incorrect username or password.</Alert>
              </Collapse>
              <TextField
                value={username}
                onChange={handleChangeUsername}
                required
                fullWidth
                id="sign-in-username"
                label="Username"
                autoFocus
                sx={{mt: 2, mb: 1}}
              />
              <TextField
                value={password}
                onChange={handleChangePassword}
                required
                fullWidth
                id="sign-in-password"
                label="Password"
                type="password"
                sx={{mt: 2, mb: 1}}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                disabled={this.state.disabled}
                sx={{mt: 2, mb: 3}}
              >
                Log In
              </Button>
            </Box>
          </Box>
        </Grid>
        <Snackbar open>
          <Alert severity="info">
            admin:admin <br/>
            user1:user1 <br/>
            user2:user2
          </Alert>
        </Snackbar>
      </Grid>
    );
  }
}

export default React.forwardRef((props, ref) => <LogInForm {...props} forwardedRef={ref}/>);
