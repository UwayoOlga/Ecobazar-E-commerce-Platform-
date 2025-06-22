import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ProductCard = ({ product, fetchProducts }) => {
  const navigate = useNavigate();

  const deleteProduct = async () => {
    await axios.delete(`http://localhost:8080/api/products/${product.id}`);
    fetchProducts();
  };

  return (
    <div className="bg-white p-4 rounded shadow">
      <h2 className="text-xl font-bold">{product.name}</h2>
      <p>{product.description}</p>
      <p className="text-green-600 font-semibold">${product.price}</p>
      <div className="flex gap-2 mt-2">
        <button
          onClick={() => navigate(`/edit/${product.id}`)}
          className="px-3 py-1 bg-blue-500 text-white rounded"
        >
          Edit
        </button>
        <button
          onClick={deleteProduct}
          className="px-3 py-1 bg-red-500 text-white rounded"
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
