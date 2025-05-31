import { useState } from 'react';
import axios from 'axios';

const ProductForm = ({ fetchProducts }) => {
  const [form, setForm] = useState({
    name: '',
    description: '',
    price: '',
    imageUrl: '',
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await axios.post('http://localhost:8080/api/products', {
      ...form,
      price: parseFloat(form.price),
    });
    setForm({ name: '', description: '', price: '', imageUrl: '' });
    fetchProducts();
  };

  return (
    <form onSubmit={handleSubmit} className="p-4 bg-white rounded shadow space-y-4">
      <input
        type="text"
        name="name"
        value={form.name}
        onChange={handleChange}
        placeholder="Name"
        className="border p-2 w-full"
        required
      />
      <input
        type="text"
        name="description"
        value={form.description}
        onChange={handleChange}
        placeholder="Description"
        className="border p-2 w-full"
        required
      />
      <input
        type="number"
        name="price"
        value={form.price}
        onChange={handleChange}
        placeholder="Price"
        className="border p-2 w-full"
        required
      />
      <input
        type="text"
        name="imageUrl"
        value={form.imageUrl}
        onChange={handleChange}
        placeholder="Image URL"
        className="border p-2 w-full"
        required
      />
      <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">
        Add Product
      </button>
    </form>
  );
};

export default ProductForm;
