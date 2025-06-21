import React, { useState } from 'react';

export default function ProductForm({ initialValues = {}, onSubmit, onCancel, loading }) {
  const [form, setForm] = useState({
    name: initialValues.name || '',
    description: initialValues.description || '',
    price: initialValues.price || '',
    quantityInStock: initialValues.quantityInStock || '',
    imageUrl: initialValues.imageUrl || '',
    categoryId: initialValues.categoryId || '',
  });
  const [error, setError] = useState('');

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setError('');
    if (!form.name || !form.price || !form.quantityInStock || !form.categoryId) {
      setError('Please fill in all required fields.');
      return;
    }
    try {
      await onSubmit({
        ...form,
        price: parseFloat(form.price),
        quantityInStock: parseInt(form.quantityInStock, 10),
        categoryId: parseInt(form.categoryId, 10),
      });
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 bg-white rounded shadow max-w-md mx-auto">
      <h2 className="text-xl font-bold mb-2">{initialValues.id ? 'Edit Product' : 'Add Product'}</h2>
      <div>
        <label className="block text-gray-700">Name *</label>
        <input
          name="name"
          value={form.name}
          onChange={handleChange}
          className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
      </div>
      <div>
        <label className="block text-gray-700">Description *</label>
        <textarea
          name="description"
          value={form.description}
          onChange={handleChange}
          className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
      </div>
      <div className="flex gap-4">
        <div className="flex-1">
          <label className="block text-gray-700">Price *</label>
          <input
            name="price"
            type="number"
            min="0"
            step="0.01"
            value={form.price}
            onChange={handleChange}
            className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>
        <div className="flex-1">
          <label className="block text-gray-700">Stock *</label>
          <input
            name="quantityInStock"
            type="number"
            min="0"
            value={form.quantityInStock}
            onChange={handleChange}
            className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>
      </div>
      <div>
        <label className="block text-gray-700">Image URL</label>
        <input
          name="imageUrl"
          value={form.imageUrl}
          onChange={handleChange}
          className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>
      <div>
        <label className="block text-gray-700">Category ID *</label>
        <input
          name="categoryId"
          type="number"
          min="1"
          value={form.categoryId}
          onChange={handleChange}
          className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
      </div>
      {error && <div className="text-red-500 text-sm">{error}</div>}
      <div className="flex gap-2 justify-end">
        <button
          type="button"
          onClick={onCancel}
          className="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          disabled={loading}
        >
          {loading ? 'Saving...' : (initialValues.id ? 'Update' : 'Add')}
        </button>
      </div>
    </form>
  );
}
