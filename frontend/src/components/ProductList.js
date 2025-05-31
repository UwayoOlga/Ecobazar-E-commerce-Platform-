import axios from 'axios';

const ProductList = ({ products, fetchProducts }) => {
  const deleteProduct = async (id) => {
    await axios.delete(`http://localhost:8080/api/products/${id}`);
    fetchProducts();
  };

  return (
    <div className="mt-6">
      <h2 className="text-xl font-semibold mb-4">Products</h2>
      <ul className="space-y-4">
        {products.map((product) => (
          <li key={product.id} className="p-4 bg-white rounded shadow">
            <h3 className="text-lg font-bold">{product.name}</h3>
            <p>{product.description}</p>
            <p className="text-green-600 font-semibold">${product.price}</p>
            <img src={product.imageUrl} alt={product.name} className="h-32 w-32 object-cover mt-2" />
            <button
              onClick={() => deleteProduct(product.id)}
              className="mt-2 bg-red-500 text-white px-3 py-1 rounded"
            >
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
