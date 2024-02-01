import { Route, Routes as ReactRouterRoutes } from "react-router-dom";
import HomePage from "./HomePage";
import { SingleGame } from "./SingleGame";
import { CooperationGame } from "./CooperationGame";
import { BattleGame } from "./BattleGame";
import RankPage from "./RankPage";
import ShopPage from "./ShopPage";
import OptionsPage from "./OptionsPage";
import ProfilePage from "./ProfilePage";

export function Routes() {
  return (
    <ReactRouterRoutes>
      <Route path="/" element={<HomePage />} />
      <Route path="/rank" element={<RankPage />} />
      <Route path="/shop" element={<ShopPage />} />
      <Route path="/options" element={<OptionsPage />} />
      <Route path="/user/:userId" element={<ProfilePage />} />
      <Route path="/game" element={<SingleGame.ListPage />} />
      <Route path="/game/single" element={<SingleGame.ListPage />} />
      <Route path="/game/single/:puzzleId" element={<SingleGame.IngamePage />} />
      <Route path="/game/cooperation" element={<CooperationGame.ListPage />} />
      {/* TODO: socket room의 상태에 따라 WaitingPage 또는 IngamePage 를 보여줘야한다. */}
      <Route path="/game/cooperation/:roomId" element={<CooperationGame.WaitingPage />} />
      <Route path="/game/battle" element={<BattleGame.ListPage />} />
      {/* TODO: socket room의 상태에 따라 WaitingPage 또는 IngamePage 를 보여줘야한다. */}
      <Route path="/game/battle/:roomId" element={<BattleGame.WaitingPage />} />
    </ReactRouterRoutes>
  );
}
