export default function IngameLoadingOverlay({ isShow, time }) {
  if (!isShow) {
    return null;
  }
  return <h1>{time}</h1>;
}
