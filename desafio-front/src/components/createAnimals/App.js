import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import TopBar from '../topBar/App';
import DatePicker from 'react-datepicker';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-datepicker/dist/react-datepicker.css';

const CadastrarAnimal = () => {
  const apiUrl = process.env.REACT_APP_API_URL;
  const navigate = useNavigate();
  const [nome, setNome] = useState('');
  const [descricao, setDescricao] = useState('');
  const [urlImagem, setUrlImagem] = useState('');
  const [categorias, setCategorias] = useState([]);
  const [nomeCategoria, setNomeCategoria] = useState('');
  const [dataNascimento, setDataNascimento] = useState(new Date());
  const [status, setStatus] = useState('DISPONIVEL');

  useEffect(() => {
    fetch(`${apiUrl}/api/categories`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json())
      .then(data => {
        setCategorias(data);
      })
      .catch(error => console.error('Erro ao buscar categorias:', error));
  }, []);

  const handleDateChange = (date) => {
    if (date > new Date()) {
      alert('Não é possível escolher uma data de nascimento no futuro.');
      return;
    }
    setDataNascimento(date);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newAnimal = {
      nome,
      descricao,
      urlImagem,
      nomeCategoria,
      dataNascimento: dataNascimento.toISOString().slice(0, 10),
      status
    };

    fetch(`${apiUrl}/api/animals`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newAnimal)
    })
    .then(response => response.json())
    .then(data => {
      console.log('Animal cadastrado com sucesso:', data);
      navigate("/");
    })
    .catch(error => console.error('Erro cadastrando animal:', error));
  };

  return (
    <div>
      <TopBar />
      <div className="container mt-5">
        <form className="w-50 mx-auto" onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="nome" className="form-label">Nome:</label>
            <input type="text" className="form-control" id="nome" value={nome} onChange={e => setNome(e.target.value)} required />
          </div>
          <div className="mb-3">
            <label htmlFor="descricao" className="form-label">Descrição:</label>
            <input type="text" className="form-control" id="descricao" value={descricao} onChange={e => setDescricao(e.target.value)} required />
          </div>
          <div className="mb-3">
            <label htmlFor="urlImagem" className="form-label">URL da Imagem:</label>
            <input type="text" className="form-control" id="urlImagem" value={urlImagem} onChange={e => setUrlImagem(e.target.value)} required />
          </div>
          <div className="mb-3">
            <label htmlFor="nomeCategoria" className="form-label">Categoria:</label>
            <select className="form-select" id="nomeCategoria" value={nomeCategoria} onChange={e => setNomeCategoria(e.target.value)} required>
              {categorias.map((categoria) => (
                <option key={categoria.id} value={categoria.nome}>{categoria.nome}</option>
              ))}
            </select>
          </div>
          <div className="mb-3">
            <label htmlFor="dataNascimento" className="form-label" >Data de Nascimento:</label><br></br>
            <DatePicker
              selected={dataNascimento}
              onChange={handleDateChange}
              dateFormat="dd/MM/yyyy"
              maxDate={new Date()}
              className="form-control"
              locale="pt-BR"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="status" className="form-label">Status:</label>
            <select className="form-select" id="status" value={status} onChange={e => setStatus(e.target.value)} required>
              <option value="DISPONIVEL">Disponível</option>
              <option value="ADOTADO">Adotado</option>
            </select>
          </div>
          <button type="submit" className="btn btn-success">Cadastrar</button>
        </form>
      </div>
    </div>
  );
};

export default CadastrarAnimal;
