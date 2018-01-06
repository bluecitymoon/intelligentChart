(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEgeLevelDialogController', PersonFansEgeLevelDialogController);

    PersonFansEgeLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFansEgeLevel', 'Person', 'EgeLevel'];

    function PersonFansEgeLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFansEgeLevel, Person, EgeLevel) {
        var vm = this;

        vm.personFansEgeLevel = entity;
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
        
        vm.egelevels = EgeLevel.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFansEgeLevel.id !== null) {
                PersonFansEgeLevel.update(vm.personFansEgeLevel, onSaveSuccess, onSaveError);
            } else {
                PersonFansEgeLevel.save(vm.personFansEgeLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFansEgeLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
