(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieCategoryDialogController', MovieCategoryDialogController);

    MovieCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MovieCategory', 'Movie', 'AreaType'];

    function MovieCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MovieCategory, Movie, AreaType) {
        var vm = this;

        vm.movieCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.movies = Movie.query();
        vm.areatypes = AreaType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.movieCategory.id !== null) {
                MovieCategory.update(vm.movieCategory, onSaveSuccess, onSaveError);
            } else {
                MovieCategory.save(vm.movieCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:movieCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
