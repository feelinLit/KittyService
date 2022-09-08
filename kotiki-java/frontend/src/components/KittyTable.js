import * as React from 'react';
import {DataGrid, GridRowModes} from '@mui/x-data-grid';
import {Box, IconButton} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Close';

export default function KittyTable(props) {
  const [rows, setRows] = React.useState(props.initialRows);
  const [rowModesModel, setRowModesModel] = React.useState({});

  const handleRowEditStart = (params, event) => {
    event.defaultMuiPrevented = true;
  };

  const handleRowEditStop = (params, event) => {
    event.defaultMuiPrevented = true;
  };

  const handleEditClick = (id) => () => {
    setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.Edit}});
  };

  const handleSaveClick = (id) => () => {
    setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.View}});
  };

  const handleDeleteClick = (id) => () => {
    setRows(rows.filter(row => row.id !== id));
    props.onKittyRemove(id);
    fetch('/api/kitty/' + id, {
      method: 'DELETE',
      headers: {'Authorization': props.authHeader},
    })
      .then(res => {
        if (!res.ok)
          throw new Error('Error occurred while deleting');
      })
      .catch(error => console.error(error));
  };

  const handleCancelClick = (id) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: {mode: GridRowModes.View, ignoreModifications: true},
    });
    const editedRow = rows.find((row) => row.id === id);
    if (editedRow.isNew) {
      setRows(rows.filter((row) => row.id !== id));
    }
  };

  const processRowUpdate = (newRow) => {
    const updatedRow = {...newRow, isNew: false};
    setRows(rows.map((row) => (row.id === newRow.id ? updatedRow : row)));
    fetch('/api/kitty/' + newRow.id, {
      method: 'PUT',
      headers: {'Authorization': props.authHeader, 'Content-Type': 'application/json'},
      body: JSON.stringify(newRow),
    })
      .then(res => {
        if (!res.ok)
          throw new Error('Error occurred while updating');
      })
      .catch(error => console.error(error));

    return updatedRow;
  };

  const columns = [
    {field: 'id', headerName: 'ID', width: 90},
    {field: 'name', headerName: 'Name', width: 150, editable: true},
    {
      field: 'color',
      headerName: 'Color',
      width: 120,
      type: 'singleSelect',
      valueOptions: ['BLACK', 'WHITE', 'NON_BINARY'],
      editable: true
    },
    {field: 'breed', headerName: 'Breed', width: 120, editable: true},
    {field: 'dateOfBirth', headerName: 'Date of birth', type: 'dateTime', width: 120, editable: true},
    {
      field: 'actions',
      type: 'actions',
      headerName: 'Actions',
      width: 100,
      cellClassName: 'actions',
      getActions: ({id}) => {
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

        if (isInEditMode) {
          return [
            <IconButton
              label="Save"
              onClick={handleSaveClick(id)}
            >
              <SaveIcon/>
            </IconButton>,
            <IconButton
              label="Cancel"
              className="textPrimary"
              onClick={handleCancelClick(id)}
              color="inherit"
            >
              <CancelIcon/>
            </IconButton>,
          ];
        }

        return [
          <IconButton
            label="Edit"
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          >
            <EditIcon/>
          </IconButton>,
          <IconButton
            label="Delete"
            onClick={handleDeleteClick(id)}
            color="inherit"
          >
            <DeleteIcon/>
          </IconButton>,
        ];
      },
    },
  ]

  return (
    <Box {...props.sx}>
      <DataGrid
        rows={props.initialRows}
        columns={columns}
        editMode="row"
        pageSize={5}
        rowsPerPageOptions={[5]}
        rowModesModel={rowModesModel}
        onRowEditStart={handleRowEditStart}
        onRowEditStop={handleRowEditStop}
        processRowUpdate={processRowUpdate}
        experimentalFeatures={{newEditingApi: true}}
      />
    </Box>
  );
}