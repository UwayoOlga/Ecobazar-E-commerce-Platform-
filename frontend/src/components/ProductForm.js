import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const ProductForm = ({ isEdit = false }) => {
  const [formData, setFormData] = useState({ name: '', description: '', price: '' });
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (isEdit) {
      axios.get(`http://localhost:8080/api/products/${id}`).then(response => {
        setFormData(response.data);
      });
    }
  }, [id, isEdit]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isEdit) {
      await axios.put(`http://localhost:8080/api/products/${id}`, formData);
    } else {
      await axios.post('http://localhost:8080/api/products', formData);
    }
    navigate('/');
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-md mx-auto p-4 bg-white shadow rounded">
      <input name="name" placeholder="Name" value={formData.name} onChange={handleChange} className="w-full p-2 mb-2 border" />
      <input name="description" placeholder="Description" value={formData.description} onChange={handleChange} className="w-full p-2 mb-2 border" />
      <input name="price" placeholder="Price" value={formData.price} onChange={handleChange} className="w-full p-2 mb-2 border" />
      <button type="submit" className="w-full bg-green-500 text-white p-2 rounded">
        {isEdit ? 'Update Product' : 'Add Product'}
      </button>
    </form>
  );
};

export default ProductForm;
