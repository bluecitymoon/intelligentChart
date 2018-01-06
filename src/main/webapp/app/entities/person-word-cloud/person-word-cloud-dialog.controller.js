(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWordCloudDialogController', PersonWordCloudDialogController);

    PersonWordCloudDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonWordCloud', 'Person', 'WordCloud'];

    function PersonWordCloudDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonWordCloud, Person, WordCloud) {
        var vm = this;

        vm.personWordCloud = entity;
        vm.clear = clear;
        vm.save = save;
                vm.people = [];
        vm.searchPersonWithKeyword = searchPersonWithKeyword;
        function searchPersonWithKeyword(keyword) {

            if (keyword) {

                Person.loadAllByPersonName({
                    page: 0,
                    size: 10,
                    name: keyword
                }, onSuccess, onError);
            }

            function onSuccess(data) {
                vm.people = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        vm.wordclouds = WordCloud.query({size: 5000});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personWordCloud.id !== null) {
                PersonWordCloud.update(vm.personWordCloud, onSaveSuccess, onSaveError);
            } else {
                PersonWordCloud.save(vm.personWordCloud, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personWordCloudUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
