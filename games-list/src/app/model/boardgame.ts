export interface Boardgame {
  objectId: number;
  yearPublished: number;
  minPlayers: number;
  maxPlayers: number;
  playingTime: number;
  minPlayTime: number;
  maxPlayTime: number;
  age: number;
  name: string;
  description: string;
  thumbnailImageLink?: string;
  publisher?: string;
  version?: string;
  category?: string;
  families: string[];
  gameMechanics?: string;
  bestPlayedWith: string;
  boardGameRank: number;
}
