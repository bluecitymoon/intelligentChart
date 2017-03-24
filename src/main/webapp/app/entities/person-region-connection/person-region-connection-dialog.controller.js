(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRegionConnectionDialogController', PersonRegionConnectionDialogController);

    PersonRegionConnectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonRegionConnection', 'Person', 'Region'];

    function PersonRegionConnectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonRegionConnection, Person, Region) {
        var vm = this;

        vm.personRegionConnection = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.regions = Region.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personRegionConnection.id !== null) {
                PersonRegionConnection.update(vm.personRegionConnection, onSaveSuccess, onSaveError);
            } else {
                PersonRegionConnection.save(vm.personRegionConnection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personRegionConnectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
