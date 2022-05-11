import { ReactComponent as Add } from 'icons/add.svg';
import { ReactComponent as Edit } from 'icons/edit.svg';
import { ReactComponent as Delete } from 'icons/delete.svg';
import { ReactComponent as Check } from 'icons/check.svg';
import { ReactComponent as Close } from 'icons/close.svg';
import { useState, useEffect } from 'react';
import getObj, { createNUpdateObj, pushObj } from 'utils/fetchfb';
import Alert from 'components/widgets/Alert';

const TabelRow = ({
  add, close, id, data = {}, onEdit, onAdd, onDelete,
}) => {
  const [code, setCode] = useState(data?.code || '');
  const [name, setName] = useState(data?.name || '');
  const [country, setCountry] = useState(data?.country || '');
  const [edit, setEdit] = useState(false);

  const handleSave = () => {
    const obj = {
      code,
      name,
      country,
    };
    edit ? onEdit(obj, id, () => setEdit(false)) : onAdd(obj);
  };

  useEffect(() => {
    if (edit) {
      setCode(data?.code);
      setName(data?.name);
      setCountry(data?.country);
    }
  }, [edit]);

  return (
    <div className="row frcs">
      <div className="col fccs">
        {(add || edit) ? <input onChange={(e) => setCode(e.target.value)} value={code} placeholder="code" /> : <p>{data.code}</p>}
      </div>
      <div className="col fccs">
        {(add || edit) ? <input onChange={(e) => setName(e.target.value)} value={name} placeholder="airport name" /> : <p>{data.name}</p>}
      </div>
      <div className="col fccs">
        {(add || edit) ? <input onChange={(e) => setCountry(e.target.value)} value={country} placeholder="country" /> : <p>{data.country}</p>}
      </div>
      <div className="col frsc">
        {(add || edit) ? (
          <>
            <Check className="action-btn check" onClick={handleSave} />
            <Close className="action-btn close" onClick={() => (edit ? setEdit(false) : close(false))} />
          </>
        ) : (
          <>
            <Edit className="action-btn edit" onClick={() => setEdit(true)} />
            <Delete className="action-btn delete" onClick={() => onDelete()} />
          </>
        )}
      </div>
    </div>
  );
};

const AirportManager = () => {
  const [add, setAdd] = useState(false);
  const [data, setData] = useState(null);

  const getData = async () => {
    const result = await getObj('/AirportCodes');
    setData(result);
  };

  const onAdd = (obj) => {
    pushObj('/AirportCodes', obj);
    setAdd(false);
    getData();
  };

  const onEdit = (obj, id, afterAction) => {
    createNUpdateObj(`/AirportCodes/${id}/`, obj);
    afterAction();
    getData();
  };

  const onDelete = (id) => {
    createNUpdateObj(`/AirportCodes/${id}/`, null);
    getData();
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div className="fccs manager-table">
      <div className="header">
        <h2> Manage Airport </h2>
        <button className="new-btn frcc" onClick={() => setAdd(true)} type="button">
          <Add className="add-btn" />
          <h2 className="add-lbl">Add New</h2>
        </button>
      </div>
      <div className="table">
        <div className="row table-header frcs">
          <div className="col fccs">
            <p>Code</p>
          </div>
          <div className="col fccs">
            <p>Airport Name</p>
          </div>
          <div className="col fccs">
            <p>Country</p>
          </div>
          <div className="col fccs">
            <p>Action</p>
          </div>
        </div>
        {data && Object.entries(data).map(([id, data], index) => (
          <TabelRow data={data} id={id} key={index} index={index} onEdit={onEdit} onDelete={() => onDelete(id)} />
        ))}
        {add && <TabelRow add close={setAdd} onAdd={onAdd} />}
      </div>
    </div>
  );
};
export default AirportManager;