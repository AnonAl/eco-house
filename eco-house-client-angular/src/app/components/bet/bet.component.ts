import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {BetService} from '../../services/bet/bet.service';
import {Bet} from '../../models/bet';
import {ModalDirective} from 'ngx-bootstrap/modal';
import {Auction} from '../../models/auction';
import {AuctionService} from '../../services/auction/auction.service';
import {InteractionService} from '../../services/interaction/interaction.service';
import {LoginService} from '../../services/login/login.service';
import {TranslateService} from '@ngx-translate/core';

/**
 * Component view bets history and new bet modals
 */
@Component({
  selector: 'app-bet',
  templateUrl: './bet.component.html',
  styleUrls: ['./bet.component.css']
})
export class BetComponent implements OnInit, OnDestroy {

  @ViewChild('betsModal') betsModal: ModalDirective;
  @ViewChild('newBetModal') newBetModal: ModalDirective;

  config = {
    keyboard: true,
    backdrop: false
  };

  buttonLocked: boolean = false;

  private alive: boolean = true;

  bets: Bet[];
  auction: Auction;
  newBet: number;

  unset: boolean;

  /**
   * Constructor for Bet component
   * @param {InteractionService} interact
   * @param {BetService} betService
   * @param {AuctionService} auctionService
   * @param {LoginService} auth
   * @param {TranslateService} translate
   */
  constructor(private interact: InteractionService,
              private betService: BetService,
              private auctionService: AuctionService,
              private auth: LoginService,
              private translate: TranslateService) {
  }

  /**
   * Run when component initialize
   */
  ngOnInit() {
    this.subscribeBetsHistoryCall();
    this.subscribeNewBetCall();
  }

  /**
   * Hide bets modal
   */
  hideBetsModal() {
    this.betsModal.hide();
  }

  /**
   * Hide new bet modal
   */
  hideNewBetModal() {
    this.newBetModal.hide();
  }

  /**
   * Handle submitting new bet form
   * Place new bet
   */
  onSubmitNewBet() {
    if (!this.buttonLocked) {
      let bet = new Bet(null, this.auction.id, this.auth.getUserData().userName, null, this.newBet);
      this.betService.save(bet)
        .takeWhile(() => this.alive)
        .subscribe(() => {
            this.newBetModal.hide();
            this.interact.refreshBets();
          },
          error => {
            console.log('User or eco_house not found');
            console.log(error);
            this.hideNewBetModal();
          })
    }
    this.buttonLocked = true;
  }

  /**
   * Subscribe to bets history call
   * If emitted - find bets by eco_house ID and toggle bets modal
   */
  subscribeBetsHistoryCall() {
    this.interact._betsHistoryModalCalled
      .takeWhile(() => this.alive)
      .subscribe(auction => {
        this.auction = auction;
        if (auction) {
          this.betService.findByAuctionId(auction.id)
            .takeWhile(() => this.alive)
            .subscribe(bets => {
              this.bets = bets;
            })
        }
        this.betsModal.config = this.config;
        this.betsModal.toggle();
      });
  }

  /**
   * Subscribe to new bet call
   * If emitted - toggle new bet modal
   */
  subscribeNewBetCall() {
    this.interact._newBetModalCalled
      .takeWhile(() => this.alive)
      .subscribe(auction => {
        this.auction = auction;
        if (auction) {
          this.betService.findByAuctionId(auction.id)
            .takeWhile(() => this.alive)
            .subscribe(bets => {
              this.bets = bets;
              if (this.bets.length == 0) {
                this.newBet = +(auction.product.price * 1.1).toFixed(2);
              } else {
                this.newBet = +(this.bets[this.bets.length - 1].price * 1.1).toFixed(2);
              }
            })
        }
        this.newBetModal.config = this.config;
        this.newBetModal.toggle();
        this.buttonLocked = false;
      });
  }

  /**
   * Get max bet price in bets
   * @returns {number}
   */
  getMaxBetPrice() {
    return this.bets.length ? this.bets.reduce((prev, cur) => cur.price > prev.price ? cur : prev).price :
      this.auction ? this.auction.product.price : null;
  }

  /**
   * Run when component destroy.
   * Unsubscribe all subscription.
   */
  ngOnDestroy() {
    this.alive = false;
  }

}
