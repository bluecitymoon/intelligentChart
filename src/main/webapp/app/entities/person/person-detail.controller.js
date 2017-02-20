(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Person', 'Job', 'PersonAreaPercentage', 'PersonInnovation', 'PersonExperience', 'PersonEducationBackground'];

    function PersonDetailController($scope, $rootScope, $stateParams, previousState, entity, Person, Job, PersonAreaPercentage, PersonInnovation, PersonExperience, PersonEducationBackground) {
        var vm = this;

        vm.person = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personUpdate', function(event, result) {
            vm.person = result;
        });

        $scope.areaConfig = {
            title: "",
            subtitle: '',
            height:320,
            width: 300
        };


        function handleError(error) {
            console.debug(error);
        }
        PersonAreaPercentage.loadAllByPersonId({id: vm.person.id}).$promise.then(function (areas) {
            console.debug(areas);

                var pageload = {
                    name: "",
                    datapoints: []
                };

                angular.forEach(areas, function (area) {

                    var number = area.percentage;
                    var title = area.areaType.name;
                    pageload.datapoints.push({ x: title, y: number});

                });

                $scope.areaData = [ pageload ];
        }, handleError);

        PersonInnovation.loadAllByPersonId({id: vm.person.id}).$promise.then(function (innovations) {

            $scope.innovationConfig = {
                width: 300,
                height: 320,
                polar : [
                    {
                        indicator : []
                    }
                ]
            };

            $scope.innovationData = [
                {
                    name: '',
                    type: 'radar',
                    data : [
                        {
                            value : [],
                            name : ''
                        }
                    ]
                }
            ];

            angular.forEach(innovations, function (innovation) {

                $scope.innovationConfig.polar[0].indicator.push({ text: innovation.innovationType.name, max: 1});
                $scope.innovationData[0].data[0].value.push(innovation.percentage);
            });

        }, handleError);

        PersonExperience.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {
            $scope.experiences = data;
        }, handleError);

        PersonEducationBackground.loadAllByPersonId({id: vm.person.id}).$promise.then(function (data) {

            $scope.personEducationBackgrounds = data;
        }, handleError);

        $scope.$on('$destroy', unsubscribe);
    }
})();
