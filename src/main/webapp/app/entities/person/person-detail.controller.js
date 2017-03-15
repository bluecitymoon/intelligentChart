(function () {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Job',
        'PersonAreaPercentage', 'PersonInnovation', 'PersonExperience', 'PersonEducationBackground', 'PersonConnectionLevel',
        'PersonPopularity', 'PersonPrize', 'lodash', 'PersonRegionConnection', 'PersonWordCloud', 'PersonLawBusiness', 'PersonCreditCardActivity',
        'PersonNetworkDebit', 'PersonNetworkShopping', 'PersonSocialMedia', 'PersonNetworkTexiActivity', 'PersonEndorsement', 'PersonTaxiActivity',
        'PersonPaidNetworkDebit', 'PersonIncome', 'PersonSearchCount', 'PersonMediaShowUpCount', 'PersonSocialHotDiscuss',
        'PersonFansPucharsingPower', 'PersonFansHobby', 'PersonFanPaymentTool', 'PersonFanSex', 'PersonFansEgeLevel', 'PersonFansEducationLevel',
        'PersonFanDisbribution', 'PersonFanSupportGroup', 'PersonWechatArticle', 'PersonTieBa'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Job, PersonAreaPercentage,
                                    PersonInnovation, PersonExperience, PersonEducationBackground, PersonConnectionLevel,
                                    PersonPopularity, PersonPrize, lodash, PersonRegionConnection, PersonWordCloud, PersonLawBusiness, PersonCreditCardActivity,
                                    PersonNetworkDebit, PersonNetworkShopping, PersonSocialMedia, PersonNetworkTexiActivity, PersonEndorsement, PersonTaxiActivity,
                                    PersonPaidNetworkDebit, PersonIncome, PersonSearchCount, PersonMediaShowUpCount, PersonSocialHotDiscuss,
                                    PersonFansPucharsingPower, PersonFansHobby, PersonFanPaymentTool, PersonFanSex, PersonFansEgeLevel, PersonFansEducationLevel,
                                    PersonFanDisbribution, PersonFanSupportGroup, PersonWechatArticle, PersonTieBa) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        $scope.overviewPopularityData = [{
            name: "",
            datapoints: [ {x: '人气指数', y: vm.person.popularity}]
        }];

        var unsubscribe = $rootScope.$on('intelligentChartApp:personUpdate', function (event, result) {
            vm.person = result;
        });

        var singleChartWidth = 450;
        var singleChartHeight = 450;
        $scope.areaConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'
        };

        $scope.areaNewMediaConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.connectionLevelConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'

        };

        $scope.fansPurchasingConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'

        };

        $scope.incomeConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'

        };

        $scope.fansPaymentToolConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'

        };

        $scope.fansCountConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth

        };

        $scope.fansAgeLevelConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth

        };

        $scope.fansEducationBackgroundConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth

        };

        $scope.overviewPopularityConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'

        };

        $scope.networkTaxiConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'roma'

        };

        $scope.popularityConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.outcomeShoppingConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.incomeShoppingConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth,
            theme: 'macarons'
        };

        $scope.searchCountConfig = {
            title: "",
            subtitle: '',
            height: singleChartHeight,
            width: singleChartWidth
        };

        $scope.wordCloud = {
            width: singleChartWidth,
            height: singleChartHeight - 5
        };

        function handleError(error) {
            console.debug(error);
        }

        PersonAreaPercentage.loadAllByPersonIdAndType({id: vm.person.id, type: 'cinema'}).$promise.then(function (areas) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(areas, function (area) {

                var number = area.percentage;
                var title = area.areaType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.areaData = [pageload];
        }, handleError);

        PersonAreaPercentage.loadAllByPersonIdAndType({id: vm.person.id, type: 'new_media'}).$promise.then(function (areas) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(areas, function (area) {

                var number = area.percentage;
                var title = area.areaType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.areaNewMediaData = [pageload];
        }, handleError);


        PersonInnovation.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.innovationConfig = {
                width: singleChartWidth,
                height: singleChartHeight,
                polar: [
                    {
                        indicator: []
                    }
                ]
            };

            $scope.innovationData = [
                {
                    name: '',
                    type: 'radar',
                    data: [
                        {
                            value: [],
                            name: ''
                        }
                    ]
                }
            ];

            angular.forEach(innovations, function (innovation) {

                $scope.innovationConfig.polar[0].indicator.push({text: innovation.innovationType.name, max: 1});
                $scope.innovationData[0].data[0].value.push(innovation.percentage);
            });

        }, handleError);

        PersonExperience.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.experiences = data;
        }, handleError);

        PersonEducationBackground.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            $scope.personEducationBackgrounds = data;
        }, handleError);

        PersonPrize.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            if (data && data.length > 0) {

                $scope.prizes = lodash.groupBy(data, function (prize) {
                    return prize.prizeType.name;
                });
            }
        }, handleError);

        PersonConnectionLevel.loadAllByPersonId({id: vm.person.id}).$promise.then(function (levels) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(levels, function (level) {

                var number = level.percentage;
                var title = level.connectionLevel.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.connectionLevelData = [pageload];

        }, handleError);


        PersonPopularity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (pops) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(pops, function (pop) {

                var number = pop.percentage;
                var title = pop.popularityType.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.popularityData = [pageload];

        }, handleError);

        PersonRegionConnection.loadAllByPersonId({id: vm.person.id}).$promise.then(function (connections) {

            var regionData = [];
            var maxCount = 0;
            angular.forEach(connections, function (connection) {

                var count = connection.count;
                if (count > maxCount) {
                    maxCount = count;
                }

                regionData.push({name: connection.region.name, value: count});
            });

            $scope.connectionRegionConfig = {

                height: 450,
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['人脉分布']
                },
                visualMap: {
                    min: 0,
                    max: maxCount,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],
                    calculable : true
                }
            };

            $scope.connectionRegionData = [
                {
                    name: '人脉分布',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: regionData
                }
            ];

            console.debug($scope.connectionRegionData);

        }, handleError);

        PersonFanDisbribution.loadAllByPersonId({id: vm.person.id}).$promise.then(function (connections) {

            var regionData = [];
            var maxCount = 0;
            angular.forEach(connections, function (connection) {

                var count = connection.count;
                if (count > maxCount) {
                    maxCount = count;
                }

                regionData.push({name: connection.region.name, value: count});
            });

            $scope.fansDistributionConfig = {

                height: 450,
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['粉丝分布']
                },
                visualMap: {
                    min: 0,
                    max: maxCount,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],
                    calculable : true
                },
                theme: 'infographic'
            };

            $scope.fansDistributionData = [
                {
                    name: '粉丝分布',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: regionData
                }
            ];

        }, handleError);

        PersonWordCloud.loadAllByPersonId({id: vm.person.id}).$promise.then(function (words) {

            var elements = [];
            angular.forEach(words, function (word) {

                elements.push({
                    text: word.wordCloud.name,
                    size: word.count
                });
            });

            $scope.words = elements;
        }, handleError);

        PersonLawBusiness.loadAllByPersonId({id: vm.person.id}).$promise.then(function (lawBusinesses) {

            $scope.lawBusinesses = lawBusinesses;
        }, handleError);

        PersonCreditCardActivity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (creditCardActivities) {

            $scope.creditCardActivities = creditCardActivities;
        }, handleError);

        PersonNetworkDebit.loadAllByPersonId({id: vm.person.id}).$promise.then(function (networkDebits) {

            $scope.networkDebits = networkDebits;
        }, handleError);

        PersonPaidNetworkDebit.loadAllByPersonId({id: vm.person.id}).$promise.then(function (paidNetworkDebits) {

            $scope.paidNetworkDebits = paidNetworkDebits;
        }, handleError);

        PersonNetworkShopping.loadAllByPersonIdAndType({id: vm.person.id, type: 'OUTCOME'}).$promise.then(function (outcomeShopping) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(outcomeShopping, function (shopping) {

                var number = shopping.amount;
                var title = shopping.description;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.outcomeShoppingData = [pageload];

        }, handleError);

        PersonNetworkShopping.loadAllByPersonIdAndType({id: vm.person.id, type: 'INCOME'}).$promise.then(function (outcomeShopping) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(outcomeShopping, function (shopping) {

                var number = shopping.amount;
                var title = shopping.description;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.incomeShoppingData = [pageload];

        }, handleError);

        PersonSocialMedia.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            if (data && data.length > 0) {

                $scope.socialMediaAttributes = lodash.groupBy(data, function (media) {
                    return media.socialMediaType.name;
                });
            }

        }, handleError);

        PersonNetworkTexiActivity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (activities) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(activities, function (activity) {

                var number = activity.count;
                var title = activity.networkTexiCompany.name;
                pageload.datapoints.push({x: title, y: number});

            });

            pageload.datapoints = lodash.sortBy(pageload.datapoints, ['y']);
            $scope.networkTexiData = [pageload];

        }, handleError);


        PersonEndorsement.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.endorsements = data;

        }, handleError);

        PersonTaxiActivity.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.taxiActivities = data;

        }, handleError);

        PersonIncome.loadAllByPersonId({id: vm.person.id}).$promise.then(function (incomes) {

            var first = {
                name: "",
                datapoints: []
            };

            var second = {
                name: "",
                datapoints: []
            };

            var third = {
                name: "",
                datapoints: []
            };

            var fourth = {
                name: "",
                datapoints: []
            };

            angular.forEach(incomes, function (income) {

                var title = income.year;
                first.datapoints.push({x: title, y: income.inCountrySalaryTotal});
                second.datapoints.push({x: title, y: income.inCountryPlusBoxTotal});
                third.datapoints.push({x: title, y: income.outCountrySalaryTotal});
                fourth.datapoints.push({x: title, y: income.outCountryPlusBoxTotal});

            });

            $scope.incomeData = [first, second, third, fourth];

        }, handleError);

        PersonSearchCount.loadAllByPersonId({id: vm.person.id}).$promise.then(function (counts) {

            var first = {
                name: "",
                datapoints: []
            };

            angular.forEach(counts, function (income) {

                var title = income.searchDate;
                first.datapoints.push({x: title, y: income.count});


            });

            $scope.searchCountData = [first];

        }, handleError);

        PersonMediaShowUpCount.loadAllByPersonId({id: vm.person.id}).$promise.then(function (counts) {

            var first = {
                name: "",
                datapoints: []
            };

            angular.forEach(counts, function (income) {

                var title = income.showUpDate;
                first.datapoints.push({x: title, y: income.count});

            });

            $scope.mediaShowupData = [first];

        }, handleError);

        PersonSocialHotDiscuss.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.hotDiscusses = data;

        }, handleError);

        PersonFansPucharsingPower.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(data, function (level) {

                var number = level.count;
                var title = level.fansPurchasingSection.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.fansPuchasingPowerData = [pageload];

        }, handleError);

        PersonFansHobby.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.fansHobbyConfig = {
                width: singleChartWidth,
                height: singleChartHeight,
                polar: [
                    {
                        indicator: []
                    }
                ]
            };

            $scope.fansHobbyData = [
                {
                    name: '',
                    type: 'radar',
                    data: [
                        {
                            value: [],
                            name: ''
                        }
                    ]
                }
            ];

            angular.forEach(innovations, function (innovation) {

                $scope.fansHobbyConfig.polar[0].indicator.push({text: innovation.hobby.name, max: 100});
                $scope.fansHobbyData[0].data[0].value.push(innovation.count);
            });

        }, handleError);

        PersonFanPaymentTool.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(data, function (level) {

                var number = level.count;
                var title = level.paymentTool.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.fansPayments = [pageload];

        }, handleError);

        PersonFanSex.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(data, function (level) {

                var number = level.count;
                var title = level.sex.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.fansCount = [pageload];

        }, handleError);

        PersonFansEgeLevel.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(data, function (level) {

                var number = level.count;
                var title = level.egeLevel.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.fansAgeLevel = [pageload];

        }, handleError);

        PersonFansEducationLevel.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            var pageload = {
                name: "",
                datapoints: []
            };

            angular.forEach(data, function (level) {

                var number = level.count;
                var title = level.educationLevel.name;
                pageload.datapoints.push({x: title, y: number});

            });

            $scope.fansEducationData = [pageload];

        }, handleError);

        PersonFanSupportGroup.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            $scope.fanGroups = data;

        }, handleError);

        PersonWechatArticle.loadLatestWechatArticleCount({id: vm.person.id}).$promise.then(function (data) {

            if (data) {

                $scope.lastestWechatAriticle = data;
            }

        }, handleError);

        PersonTieBa.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            if (data) {

                $scope.tiebas = data;
            }

        }, handleError);

        $scope.$on('$destroy', unsubscribe);
    }
})();
