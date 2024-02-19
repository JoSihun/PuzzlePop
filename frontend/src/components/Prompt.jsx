import { Dialog } from "@mui/material";

export default function Prompt() {
  return (
    <Dialog open={isOpenedDialog} onClose={handleCloseGame}>
      <DialogContent>
        <Wrapper>
          <ResultCard
            color={"red"}
            numOfUsingPositiveItem={numOfUsingItemRed.positiveItem.current}
            numOfUsingAttackItem={numOfUsingItemRed.attackItem.current}
            image={image}
            ourPercent={ourPercent}
            enemyPercent={enemyPercent}
          />
          <ResultCard
            color={"blue"}
            numOfUsingPositiveItem={numOfUsingItemBlue.positiveItem.current}
            numOfUsingAttackItem={numOfUsingItemBlue.attackItem.current}
            image={image}
            ourPercent={ourPercent}
            enemyPercent={enemyPercent}
          />
        </Wrapper>
        {/* <Grid container sx={{ width: "300px", height: "600px" }}>
            {ourTeam.map((player) => {
              <Grid item xs={3}>
                <PlayerCard player={player} color={getTeam()} gameId={getRoomId()} />
              </Grid>;
            })}
          </Grid>
          <Grid container>
            {enemyTeam.map((player) => {
              <Grid item xs={3}>
                <PlayerCard
                  player={player}
                  color={getTeam() === "red" ? "red" : "blue"}
                  gameId={getRoomId()}
                />
              </Grid>;
            })}
          </Grid> */}
      </DialogContent>
    </Dialog>
  );
}
